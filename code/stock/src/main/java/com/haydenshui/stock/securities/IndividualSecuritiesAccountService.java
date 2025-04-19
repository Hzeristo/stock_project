package com.haydenshui.stock.securities;

import com.haydenshui.stock.constants.RocketMQConstants;
import com.haydenshui.stock.lib.annotation.ServiceLog;
import com.haydenshui.stock.lib.dto.securities.IndividualSecuritiesAccountDTO;
import com.haydenshui.stock.lib.dto.securities.SecuritiesMapper;
import com.haydenshui.stock.lib.entity.account.*;
import com.haydenshui.stock.lib.exception.ResourceAlreadyExistsException;
import com.haydenshui.stock.lib.exception.ResourceNotFoundException;
import com.haydenshui.stock.utils.BeanCopyUtils;
import com.haydenshui.stock.utils.RedisUtils;
import com.haydenshui.stock.utils.RocketMQUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class IndividualSecuritiesAccountService implements SecuritiesAccountService<IndividualSecuritiesAccount, IndividualSecuritiesAccountDTO> {

    private final IndividualSecuritiesAccountRepository repository;

    private final PasswordEncoder passwordEncoder;

    public IndividualSecuritiesAccountService(IndividualSecuritiesAccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    @ServiceLog
    public IndividualSecuritiesAccount createAccount(IndividualSecuritiesAccountDTO dto) {
        Optional<IndividualSecuritiesAccount> existingAccount = repository.findByAccountNumber(dto.getAccountNumber());
        existingAccount.ifPresent(acc -> {
            throw new ResourceAlreadyExistsException("Securities", acc.getAccountNumber());
        });
        IndividualSecuritiesAccount account = SecuritiesMapper.toEntity(dto);
        return repository.save(account);
    }

    @Override
    @ServiceLog
    public IndividualSecuritiesAccount getAccountById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("securities", "[id: " + id.toString() + "]"));
    }

    @Override
    @ServiceLog
    public IndividualSecuritiesAccount getAccountByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new ResourceNotFoundException("securities", "[accountNumber: " + accountNumber + "]"));
    }

    @Override
    @ServiceLog
    public IndividualSecuritiesAccount getAccountByIdCardNo(String idCardNo) {
        return repository.findByIdCardNo(idCardNo)
            .orElseThrow(() -> new ResourceNotFoundException("securities", "[idCardNo: " + idCardNo + "]"));
    }

    @Override
    @Transactional
    @ServiceLog
    public IndividualSecuritiesAccount updateAccount(IndividualSecuritiesAccountDTO dto) {
        Optional<IndividualSecuritiesAccount> existingAccount = repository.findById(dto.getId());
        if (existingAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + dto.getId().toString() + "]");
        }
        IndividualSecuritiesAccount existing = existingAccount.get();
        IndividualSecuritiesAccount patch = SecuritiesMapper.toEntity(dto);
        BeanCopyUtils.copyNonNullProperties(patch, existing);
        existing.setPassword(passwordEncoder.encode(existing.getPassword()));
        return repository.save(existing);
    }

    @Override
    @Transactional
    @ServiceLog
    /**
     * Updates an existing securities account.
     * <p>
     * This method will update the securities account information,
     * excluding the modification of the associated capital account list (capitalAccountIds).
     * If the account does not exist, a ResourceNotFoundException will be thrown.
     * </p>
     * 
     * @param account the account to update
     * @return an Optional containing the updated IndividualSecuritiesAccount if successful
     * @throws ResourceNotFoundException if the account is not found
     */
    public IndividualSecuritiesAccount updateAccount(IndividualSecuritiesAccount account) {
        Optional<IndividualSecuritiesAccount> existingAccount = repository.findById(account.getId());
        if (existingAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + account.getId().toString() + "]");
        }
        IndividualSecuritiesAccount existing = existingAccount.get();
        BeanCopyUtils.copyNonNullProperties(account, existing);
        existing.setPassword(passwordEncoder.encode(existing.getPassword()));
        return repository.save(existing);
    }

    @Override
    @Transactional
    @ServiceLog
    public IndividualSecuritiesAccount disableAccount(IndividualSecuritiesAccountDTO dto) {
        Optional<IndividualSecuritiesAccount> existingAccount = repository.findById(dto.getId()); 
        if (existingAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + dto.getId().toString() + "]");
        }
        IndividualSecuritiesAccount existing = existingAccount.get();

        List<Long> capitalAccountIds = existing.getCapitalAccountIds();
        for(Long id : capitalAccountIds) {
            // TODO: add logic to check list of ids
        }
        existing.setStatus(AccountStatus.CLOSED);
        return repository.save(existing);
    }

    @Override
    @Transactional
    @ServiceLog
    public IndividualSecuritiesAccount disableAccount(IndividualSecuritiesAccount account) {
        Optional<IndividualSecuritiesAccount> existingAccount = repository.findById(account.getId()); 
        if (existingAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + account.getId().toString() + "]");
        }
        IndividualSecuritiesAccount existing = existingAccount.get();

        List<Long> capitalAccountIds = existing.getCapitalAccountIds();
        List<CompletableFuture<Boolean>> checks = new ArrayList<>();
        for(Long id : capitalAccountIds) {
            checks.add(checkCapitals(id));
        }
        CompletableFuture<Void> allChecks = CompletableFuture.allOf(checks.toArray(new CompletableFuture[0]));
        allChecks.join();  
        if (!checks.stream().allMatch(CompletableFuture::join)) {
            throw new IllegalStateException("Some capital accounts are invalid.");
        }

        existing.setStatus(AccountStatus.CLOSED);
        return repository.save(existing);
    }

    private CompletableFuture<Boolean> checkCapitals(Long capitalAccountId) {
        return CompletableFuture.supplyAsync(() -> {
            RocketMQUtils.sendMessageWithTag(
                "capital",
                RocketMQConstants.TOPIC_CAPITAL,
                RocketMQConstants.TAG_CAPITAL_CHECK,
                ""
            );
            return true;
        });
    }

    @Override
    @Transactional
    @ServiceLog
    public IndividualSecuritiesAccount reportAccountLoss(IndividualSecuritiesAccountDTO dto) {
        Optional<IndividualSecuritiesAccount> optionalAccount = repository.findById(dto.getId());
        if (optionalAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + dto.getId() + "]");
        }
        IndividualSecuritiesAccount account = optionalAccount.get();
        if (account.getStatus() == AccountStatus.SUSPENDED) {
            throw new IllegalStateException("The account is already reported as lost.");
        }
        try {
            if (checkTradeOrders(account.getId()).get(10, TimeUnit.SECONDS)) {
                throw new IllegalStateException("The account has trade orders.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Thread was interrupted while checking trade orders.");
        } catch (TimeoutException e) {
            throw new IllegalStateException("Failed to check trade orders: Timeout. " + e.getCause().getMessage(), e);
        } catch (ExecutionException e) {
            throw new IllegalStateException("Failed to check trade orders: " + e.getCause().getMessage(), e);
        }
    
        account.setStatus(AccountStatus.SUSPENDED);
        return repository.save(account);
    }

    private CompletableFuture<Boolean> checkTradeOrders(Long securitiesAccountId) {
        return CompletableFuture.supplyAsync(() -> {
            //TODO: add logic to check trade orders
            return true; 
        });
    }

    @Override
    @Transactional
    @ServiceLog
    public IndividualSecuritiesAccount restoreAccount(IndividualSecuritiesAccountDTO dto) {
        Optional<IndividualSecuritiesAccount> optionalAccount = repository.findById(dto.getId());
        if (optionalAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + dto.getId() + "]");
        }

        IndividualSecuritiesAccount account = optionalAccount.get();
        if (account.getStatus() != AccountStatus.SUSPENDED) {
            throw new IllegalStateException("Only suspended accounts can be restored.");
        }

        // try {
        //     if (checkTradeOrders(account.getId()).get(10, TimeUnit.SECONDS)) {
        //         throw new IllegalStateException("The account has trade orders and cannot be restored.");
        //     }
        // } catch (InterruptedException e) {
        //     Thread.currentThread().interrupt();
        //     throw new IllegalStateException("Thread was interrupted while checking trade orders.");
        // } catch (TimeoutException e) {
        //     throw new IllegalStateException("Failed to check trade orders: Timeout. " + e.getCause().getMessage(), e);
        // } catch (ExecutionException e) {
        //     throw new IllegalStateException("Failed to check trade orders: " + e.getCause().getMessage(), e);
        // }

        account.setStatus(AccountStatus.ACTIVE);
        return repository.save(account);
    }


}
