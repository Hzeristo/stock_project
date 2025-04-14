package com.haydenshui.stock.securities;

import com.haydenshui.stock.lib.annotation.ServiceLog;
import com.haydenshui.stock.lib.dto.securities.CorporateSecuritiesAccountDTO;
import com.haydenshui.stock.lib.dto.securities.SecuritiesMapper;
import com.haydenshui.stock.lib.entity.account.*;
import com.haydenshui.stock.lib.exception.ResourceAlreadyExistsException;
import com.haydenshui.stock.lib.exception.ResourceNotFoundException;
import com.haydenshui.stock.utils.BeanCopyUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class CorporateSecuritiesAccountService implements SecuritiesAccountService<CorporateSecuritiesAccount, CorporateSecuritiesAccountDTO> {
    
    private final CorporateSecuritiesAccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    public CorporateSecuritiesAccountService(CorporateSecuritiesAccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    @ServiceLog
    public CorporateSecuritiesAccount createAccount(CorporateSecuritiesAccountDTO dto) {
        Optional<CorporateSecuritiesAccount> existingAccount = repository.findByAccountNumber(dto.getAccountNumber());
        existingAccount.ifPresent(acc -> {
            throw new ResourceAlreadyExistsException("Securities", acc.getAccountNumber());
        });
        CorporateSecuritiesAccount account = SecuritiesMapper.toEntity(dto);
        return repository.save(account);
        //TODO: add logic to check createdAt and status
    }

    @Override
    @ServiceLog
    public CorporateSecuritiesAccount getAccountById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("securities", "[id: " + id.toString() + "]"));
    }

    @Override
    @ServiceLog
    public CorporateSecuritiesAccount getAccountByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber).orElseThrow(() -> new ResourceNotFoundException("securities", "[accountNumber: " + accountNumber + "]"));
    }

    @Override
    @ServiceLog
    public CorporateSecuritiesAccount getAccountByIdCardNo(String idCardNo) {
        return repository.findByIdCardNo(idCardNo).orElseThrow(() -> new ResourceNotFoundException("securities", "[idCardNo: " + idCardNo + "]"));
    }

    @Override
    @Transactional
    @ServiceLog
    public CorporateSecuritiesAccount updateAccount(CorporateSecuritiesAccountDTO dto) {
        Optional<CorporateSecuritiesAccount> existingAccount = repository.findById(dto.getId());
        if (existingAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + dto.getId().toString() + "]");
        }
        CorporateSecuritiesAccount existing = existingAccount.get();
        CorporateSecuritiesAccount patch = SecuritiesMapper.toEntity(dto);
        BeanCopyUtils.copyNonNullProperties(patch, existing);
        existing.setPassword(passwordEncoder.encode(existing.getPassword()));
        return repository.save(existing);   
    }

    @Override
    @Transactional
    @ServiceLog
    public CorporateSecuritiesAccount updateAccount(CorporateSecuritiesAccount account) {
        Optional<CorporateSecuritiesAccount> existingAccount = repository.findById(account.getId());
        if (existingAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + account.getId().toString() + "]");
        }
        CorporateSecuritiesAccount existing = existingAccount.get();
        BeanCopyUtils.copyNonNullProperties(account, existing);
        existing.setPassword(passwordEncoder.encode(existing.getPassword()));
        return repository.save(existing);    
    }

    @Override
    @Transactional
    @ServiceLog
    public CorporateSecuritiesAccount disableAccount(CorporateSecuritiesAccountDTO dto) {
        Optional<CorporateSecuritiesAccount> existingAccount = repository.findById(dto.getId());
        if (existingAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + dto.getId().toString() + "]");
        }
        CorporateSecuritiesAccount existing = existingAccount.get();

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
    public CorporateSecuritiesAccount disableAccount(CorporateSecuritiesAccount account) {
        Optional<CorporateSecuritiesAccount> existingAccount = repository.findById(account.getId());
        if (existingAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + account.getId().toString() + "]");
        }
        CorporateSecuritiesAccount existing = existingAccount.get();

        List<Long> capitalAccountIds = existing.getCapitalAccountIds();
        for(Long id : capitalAccountIds) {
            // TODO: add logic to check list of ids
        }
        existing.setStatus(AccountStatus.CLOSED);
        return repository.save(existing);    
    }

    private CompletableFuture<Boolean> checkCapitals(Long securitiesAccountId) {
        return CompletableFuture.supplyAsync(() -> {
            return true;
        });
    }

    @Override
    @Transactional
    @ServiceLog
    public CorporateSecuritiesAccount reportAccountLoss(CorporateSecuritiesAccountDTO dto) {
    Optional<CorporateSecuritiesAccount> optionalAccount = repository.findById(dto.getId());
        if (optionalAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + dto.getId() + "]");
        }
        CorporateSecuritiesAccount account = optionalAccount.get();
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
    public CorporateSecuritiesAccount restoreAccount(CorporateSecuritiesAccountDTO dto) {
        Optional<CorporateSecuritiesAccount> optionalAccount = repository.findById(dto.getId());
        if (optionalAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + dto.getId() + "]");
        }

        CorporateSecuritiesAccount account = optionalAccount.get();
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
    


