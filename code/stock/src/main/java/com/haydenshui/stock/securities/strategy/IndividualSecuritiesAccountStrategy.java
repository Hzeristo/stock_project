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
import com.haydenshui.stock.lib.dto.securities.IndividualSecuritiesAccountDTO;
import com.haydenshui.stock.lib.dto.securities.SecuritiesMapper;
import com.haydenshui.stock.lib.entity.account.AccountStatus;
import com.haydenshui.stock.lib.entity.account.IndividualSecuritiesAccount;
import com.haydenshui.stock.lib.exception.ResourceAlreadyExistsException;
import com.haydenshui.stock.lib.exception.ResourceNotFoundException;
import com.haydenshui.stock.lib.msg.TransactionMessage;
import com.haydenshui.stock.securities.IndividualSecuritiesAccountRepository;
import com.haydenshui.stock.utils.BeanCopyUtils;
import com.haydenshui.stock.utils.RedisUtils;
import com.haydenshui.stock.utils.RocketMQUtils;

public class IndividualSecuritiesAccountStrategy implements SecuritiesAccountStrategy<IndividualSecuritiesAccount, IndividualSecuritiesAccountDTO> {

     private final IndividualSecuritiesAccountRepository repository;

    private final PasswordEncoder passwordEncoder;

    public IndividualSecuritiesAccountStrategy(IndividualSecuritiesAccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean matches(String type) {
        return false;
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
    public boolean validate(IndividualSecuritiesAccountDTO dto) {
        Optional<IndividualSecuritiesAccount> existingAccount = repository.findByAccountNumber(dto.getAccountNumber());
        existingAccount.ifPresent(acc -> {
            throw new ResourceAlreadyExistsException("Securities", "[AccountNumber: " + acc.getAccountNumber() + "]");
        });
        IndividualSecuritiesAccount existing = existingAccount.get();
        IndividualSecuritiesAccount account = SecuritiesMapper.toEntity(dto);
        return existing.getPassword().equals(passwordEncoder.encode(account.getPassword()));
    }

    @Override
    @Transactional
    @ServiceLog
    public IndividualSecuritiesAccount createAccount(IndividualSecuritiesAccountDTO dto) {
        Optional<IndividualSecuritiesAccount> existingAccount = repository.findByAccountNumber(dto.getAccountNumber());
        existingAccount.ifPresent(acc -> {
            throw new ResourceAlreadyExistsException("Securities", "[AccountNumber: " + acc.getAccountNumber() + "]");
        });
        IndividualSecuritiesAccount account = SecuritiesMapper.toEntity(dto);
        account.setStatus(AccountStatus.fromString(dto.getStatus()));
        account.setPassword(passwordEncoder.encode(account.getPassword()));
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
        existing.setStatus(AccountStatus.fromString(dto.getStatus()));
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
    @LocalTcc
    @ServiceLog
    public void tryDisableAccount(IndividualSecuritiesAccountDTO dto) {
        Optional<IndividualSecuritiesAccount> existingAccount = repository.findById(dto.getId()); 
        if (existingAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + dto.getId().toString() + "]");
        }
        IndividualSecuritiesAccount existing = existingAccount.get();

        List<Long> capitalAccountIds = existing.getCapitalAccountIds();
        initPendingCheckAndSendMessage(dto.getId(), capitalAccountIds);
    }

    @Lock(lockKey = "LOCK:NORMAL:SECURITIES:{account.id}")
    @Override
    @LocalTcc
    @ServiceLog
    public void tryDisableAccount(IndividualSecuritiesAccount account) {
        Optional<IndividualSecuritiesAccount> existingAccount = repository.findById(account.getId()); 
        if (existingAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + account.getId().toString() + "]");
        }
        IndividualSecuritiesAccount existing = existingAccount.get();

        List<Long> capitalAccountIds = existing.getCapitalAccountIds();
        initPendingCheckAndSendMessage(account.getId(), capitalAccountIds);
    }

    @Lock(lockKey = "LOCK:NORMAL:SECURITIES:{dto.securitiesAccountId}")
    @Override
    @LocalTcc
    @ServiceLog
    public IndividualSecuritiesAccount confirmDisableAccount(CheckDTO dto) {
        String redisKey = "tcc:close:" + dto.getSecuritiesAccountId();
        String capitalAccountId = dto.getCapitalAccountId().toString();

        String status = dto.isPassed() ? "SUCCESS" : "FAILURE";
        String oldStatus = RedisUtils.hGet(redisKey, capitalAccountId);
        if ("PENDING".equals(oldStatus)) {
            RedisUtils.hSet(redisKey, capitalAccountId, status);
        }

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
            throw new IllegalStateException("Some capital accounts can not disable.");
        }

        IndividualSecuritiesAccount account = repository.findById(dto.getSecuritiesAccountId())
            .orElseThrow(() -> new ResourceNotFoundException("securities", "[id: " + dto.getSecuritiesAccountId() + "]"));
        account.setStatus(AccountStatus.CLOSED);
        return repository.save(account);
    }

    @Lock(lockKey = "LOCK:NORMAL:SECURITIES:{dto.securitiesAccountId}")
    @Override
    @LocalTcc
    @ServiceLog
    public void tryReportAccountLoss(IndividualSecuritiesAccountDTO dto) {
        Optional<IndividualSecuritiesAccount> optionalAccount = repository.findById(dto.getId());
        if (optionalAccount.isEmpty()) {
            throw new ResourceNotFoundException("securities", "[id: " + dto.getId() + "]");
        }
        IndividualSecuritiesAccount existing = optionalAccount.get();
        if (existing.getStatus() == AccountStatus.SUSPENDED) {
            throw new IllegalStateException("The account is already reported as lost.");
        }
        //TODO: check what
    }

    @Lock(lockKey = "LOCK:NORMAL:SECURITIES:{dto.securitiesAccountId}")
    @Override
    @LocalTcc
    @ServiceLog
    public IndividualSecuritiesAccount comfirmAccountLoss(CheckDTO dto) {
        //TODO: check what
        throw new UnsupportedOperationException("Unimplemented method");
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
        account.setStatus(AccountStatus.ACTIVE);
        return repository.save(account);
    }
    
}
