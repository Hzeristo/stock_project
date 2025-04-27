package com.haydenshui.stock.securities.strategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson2.JSON;
import com.haydenshui.stock.constants.RocketMQConstants;
import com.haydenshui.stock.lib.annotation.LocalTcc;
import com.haydenshui.stock.lib.annotation.ServiceLog;
import com.haydenshui.stock.lib.dto.capital.CapitalCheckDTO;
import com.haydenshui.stock.lib.dto.securities.CorporateSecuritiesAccountDTO;
import com.haydenshui.stock.lib.dto.securities.SecuritiesMapper;
import com.haydenshui.stock.lib.entity.account.AccountStatus;
import com.haydenshui.stock.lib.entity.account.CorporateSecuritiesAccount;
import com.haydenshui.stock.lib.exception.ResourceAlreadyExistsException;
import com.haydenshui.stock.lib.exception.ResourceNotFoundException;
import com.haydenshui.stock.lib.msg.TransactionMessage;
import com.haydenshui.stock.securities.CorporateSecuritiesAccountRepository;
import com.haydenshui.stock.utils.BeanCopyUtils;
import com.haydenshui.stock.utils.RedisUtils;
import com.haydenshui.stock.utils.RocketMQUtils;

public class CorporateSecuritiesAccountStrategy implements SecuritiesAccountStrategy<CorporateSecuritiesAccount, CorporateSecuritiesAccountDTO> {
        
    private final CorporateSecuritiesAccountRepository repository;

    private final PasswordEncoder passwordEncoder;

    public CorporateSecuritiesAccountStrategy(CorporateSecuritiesAccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean matches(String type) {
        return type.equals("corporate");
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
    @LocalTcc
    @ServiceLog
    public void tryDisableAccount(CorporateSecuritiesAccountDTO dto) {
        Optional<CorporateSecuritiesAccount> existingAccount = repository.findById(dto.getId()); 
        if (existingAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + dto.getId().toString() + "]");
        }
        CorporateSecuritiesAccount existing = existingAccount.get();

        List<Long> capitalAccountIds = existing.getCapitalAccountIds();
        for (var id : capitalAccountIds){
            RocketMQUtils.sendMessageWithTag(
                "securities",
                RocketMQConstants.TOPIC_CAPITAL,
                RocketMQConstants.TAG_CAPITAL_VALIDITY_CHECK,
                id.toString()
            );
        }
    }

    @Override
    @LocalTcc
    @ServiceLog
    public void tryDisableAccount(CorporateSecuritiesAccount account) {
        Optional<CorporateSecuritiesAccount> existingAccount = repository.findById(account.getId()); 
        if (existingAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + account.getId().toString() + "]");
        }
        CorporateSecuritiesAccount existing = existingAccount.get();

        List<Long> capitalAccountIds = existing.getCapitalAccountIds();
        for (var id : capitalAccountIds){
            RedisUtils.hSet("tcc:close:" + account.getId(), id.toString(), "PENDING");
            CapitalCheckDTO dto = new CapitalCheckDTO(id, account.getId(), "corporate", false, "");
            RocketMQUtils.sendMessageWithTag(
                "securities",
                RocketMQConstants.TOPIC_CAPITAL,
                RocketMQConstants.TAG_CAPITAL_VALIDITY_CHECK,
                JSON.toJSONString(TransactionMessage.<CapitalCheckDTO>builder()
                    .businessAction("capital-check")
                    .payload(dto)
                )
            );
        }
        RedisUtils.expire("tcc:close:" + account.getId(), 5, TimeUnit.MINUTES);
    }

    @LocalTcc
    @ServiceLog
    public CorporateSecuritiesAccount confirmDisableAccount(CapitalCheckDTO dto) {
        String redisKey = "tcc:close:" + dto.getSecuritiesAccountId();
        String capitalAccountId = dto.getCapitalAccountId().toString();

        String status = dto.isPassed() ? "SUCCESS" : "FAILURE";
        RedisUtils.hSet(redisKey, capitalAccountId, status);

        Map<Object, Object> checkStatusMap = RedisUtils.hGetAll(redisKey);

        boolean allChecked = checkStatusMap.values().stream()
            .noneMatch(val -> "PENDING".equals(val));

        if (!allChecked) {
            return null;
        }

        RedisUtils.delete(redisKey);

        boolean allSuccess = checkStatusMap.values().stream()
            .allMatch(val -> "SUCCESS".equals(val));
        if (!allSuccess) {
            return null;
        }
        CorporateSecuritiesAccount account = repository.findById(dto.getSecuritiesAccountId())
            .orElseThrow(() -> new ResourceNotFoundException("securities", "[id: " + dto.getSecuritiesAccountId() + "]"));
        account.setStatus(AccountStatus.CLOSED);
        return repository.save(account);
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
