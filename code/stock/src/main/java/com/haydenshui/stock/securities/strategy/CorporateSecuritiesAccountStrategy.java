package com.haydenshui.stock.securities.strategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson2.JSON;
import com.haydenshui.stock.constants.RocketMQConstants;
import com.haydenshui.stock.lib.annotation.LocalTcc;
import com.haydenshui.stock.lib.annotation.Lock;
import com.haydenshui.stock.lib.annotation.ServiceLog;
import com.haydenshui.stock.lib.dto.CheckDTO;
import com.haydenshui.stock.lib.dto.securities.CorporateSecuritiesAccountDTO;
import com.haydenshui.stock.lib.dto.securities.SecuritiesMapper;
import com.haydenshui.stock.lib.entity.account.AccountStatus;
import com.haydenshui.stock.lib.entity.account.CapitalAccountType;
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

    private void initPendingCheckAndSendMessage(Long securitiesAccountId, List<Long> capitalAccountIds) {
        String redisKey = "tcc:close:" + securitiesAccountId;
    
        // 1. Capital account checks
        for (var id : capitalAccountIds) {
            setPendingAndSendCheck(redisKey, id.toString(),
                new CheckDTO(id, securitiesAccountId, "corporate", false, ""),
                RocketMQConstants.TOPIC_CAPITAL,
                RocketMQConstants.TAG_CAPITAL_VALIDITY_CHECK,
                "capital-check"
            );
        }
    
        // 2. Trade check
        setPendingAndSendCheck(redisKey, "trade",
            new CheckDTO(securitiesAccountId, null, "corporate", false, ""),
            RocketMQConstants.TOPIC_TRADE,
            RocketMQConstants.TAG_TRADE_VALIDITY_CHECK,
            "trade-check"
        );
    
        // 3. Position check
        setPendingAndSendCheck(redisKey, "position",
            new CheckDTO(securitiesAccountId, null, "corporate", false, ""),
            RocketMQConstants.TOPIC_TRADE,
            RocketMQConstants.TAG_TRADE_VALIDITY_CHECK,
            "trade-check"
        );
    
        // 4. Set key expiration
        RedisUtils.expire(redisKey, 5, TimeUnit.MINUTES);
    }
    
    private void setPendingAndSendCheck(String redisKey, String field, CheckDTO dto, String topic, String tag, String businessAction) {
        RedisUtils.hSet(redisKey, field, "PENDING");
        RocketMQUtils.sendMessageWithTag(
            "securities",
            topic,
            tag,
            JSON.toJSONString(TransactionMessage.<CheckDTO>builder()
                .businessAction(businessAction)
                .payload(dto)
            )
        );
    }
    

    @Deprecated
    @Override
    @ServiceLog
    public boolean validate(CorporateSecuritiesAccountDTO dto) {
        Optional<CorporateSecuritiesAccount> existingAccount = repository.findByAccountNumber(dto.getAccountNumber());
        existingAccount.ifPresent(acc -> {
            throw new ResourceAlreadyExistsException("Securities", "[AccountNumber: " + acc.getAccountNumber() + "]");
        });
        CorporateSecuritiesAccount existing = existingAccount.get();
        CorporateSecuritiesAccount account = SecuritiesMapper.toEntity(dto);
        return existing.getPassword().equals(passwordEncoder.encode(account.getPassword()));
    }

    @Override
    @Transactional
    @ServiceLog
    public CorporateSecuritiesAccount createAccount(CorporateSecuritiesAccountDTO dto) {
        Optional<CorporateSecuritiesAccount> existingAccount = repository.findByAccountNumber(dto.getAccountNumber());
        existingAccount.ifPresent(acc -> {
            throw new ResourceAlreadyExistsException("Securities", "[AccountNumber: " + acc.getAccountNumber() + "]");
        });
        CorporateSecuritiesAccount account = SecuritiesMapper.toEntity(dto);
        account.setStatus(AccountStatus.fromString(dto.getStatus()));
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return repository.save(account);
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
        existing.setStatus(AccountStatus.fromString(dto.getStatus()));
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
    
    // DONE
    @Lock(lockKey = "LOCK:NORMAL:SECURITIES:{dto.securitiesAccountId}")
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
        initPendingCheckAndSendMessage(dto.getId(), capitalAccountIds);
    }

    // DONE
    @Lock(lockKey = "LOCK:NORMAL:SECURITIES:{account.id}")
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
        //check capital
        initPendingCheckAndSendMessage(account.getId(), capitalAccountIds);
    }

    // DONE
    @Lock(lockKey = "LOCK:NORMAL:SECURITIES:{dto.securitiesAccountId}")
    @Override
    @LocalTcc
    @ServiceLog
    public CorporateSecuritiesAccount confirmDisableAccount(CheckDTO dto) {
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

    @Lock(lockKey = "LOCK:NORMAL:SECURITIES:{dto.securitiesAccountId}")
    @Override
    @LocalTcc
    @ServiceLog
    public void tryReportAccountLoss(CorporateSecuritiesAccountDTO dto) {
        Optional<CorporateSecuritiesAccount> optionalAccount = repository.findById(dto.getId());
        if (optionalAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + dto.getId() + "]");
        }
        CorporateSecuritiesAccount existing = optionalAccount.get();
        if (existing.getStatus() == AccountStatus.SUSPENDED) {
            throw new IllegalStateException("The account is already reported as lost.");
        }
        //TODO: check what
    }

    @Lock(lockKey = "LOCK:NORMAL:SECURITIES:{dto.securitiesAccountId}")
    @Override
    @LocalTcc
    @ServiceLog
    public CorporateSecuritiesAccount comfirmAccountLoss(CheckDTO dto) {
        //TODO: check what
        throw new UnsupportedOperationException("Unimplemented method");
        // String redisKey = "tcc:close:" + dto.getSecuritiesAccountId();
        // String capitalAccountId = dto.getCapitalAccountId().toString();

        // String status = dto.isPassed() ? "SUCCESS" : "FAILURE";
        // String oldStatus = RedisUtils.hGet(redisKey, capitalAccountId);
        // if ("PENDING".equals(oldStatus)) {
        //     RedisUtils.hSet(redisKey, capitalAccountId, status);
        // }

        // Map<Object, Object> checkStatusMap = RedisUtils.hGetAll(redisKey);

        // boolean allChecked = checkStatusMap.values().stream()
        //     .noneMatch(val -> "PENDING".equals(val));

        // if (!allChecked) {
        //     return null;
        // }

        // RedisUtils.delete(redisKey);

        // boolean allSuccess = checkStatusMap.values().stream()
        //     .allMatch(val -> "SUCCESS".equals(val));

        // if (!allSuccess) {
        //     throw new IllegalStateException("Some capital accounts can not disable.");
        // }

        // CorporateSecuritiesAccount account = repository.findById(dto.getSecuritiesAccountId())
        //     .orElseThrow(() -> new ResourceNotFoundException("securities", "[id: " + dto.getSecuritiesAccountId() + "]"));
        // account.setStatus(AccountStatus.SUSPENDED);
        // return repository.save(account);
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
        account.setStatus(AccountStatus.ACTIVE);
        return repository.save(account);    
    }


}
