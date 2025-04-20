package com.haydenshui.stock.capital.strategy;

import java.math.BigDecimal;

import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.haydenshui.stock.capital.CapitalAccountRepository;
import com.haydenshui.stock.lib.annotation.Lock;
import com.haydenshui.stock.lib.annotation.NoTransactional;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountTransactionDTO;
import com.haydenshui.stock.lib.entity.account.CapitalAccount;
import com.haydenshui.stock.lib.entity.account.CapitalAccountType;
import com.haydenshui.stock.lib.entity.tcc.TccContext;
import com.haydenshui.stock.lib.exception.ResourceNotFoundException;
import com.haydenshui.stock.utils.RedisUtils;


@Component
public class TradeAccountStrategy extends AbstractCapitalAccountStrategy {

    public TradeAccountStrategy(CapitalAccountRepository capitalAccountRepository, PasswordEncoder passwordEncoder) {
        super(capitalAccountRepository, passwordEncoder);
    }

    @Override
    public boolean matches(CapitalAccountType type) {
        return type.isTradeAccount();
    }
    @Override
    public CapitalAccount disableAccount(CapitalAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disableAccount'");
    }

    @Override
    @Transactional
    public CapitalAccount restoreAccount(CapitalAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restoreAccount'");
    }

    @Override
    @Transactional
    public CapitalAccount reportAccountLoss(CapitalAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reportAccountLoss'");
    }

    @Override
    @Transactional
    public CapitalAccount deposit(CapitalAccountTransactionDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deposit'");
    }

    @Override
    @Transactional
    public CapitalAccount withdraw(CapitalAccountTransactionDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withdraw'");
    }

    @Override
    @Lock(lockKey = "LOCK:TCC:CAPITAL:{capitalDTO.capitalAccountId}")
    @NoTransactional
    public void freezeAmount(TccContext context, CapitalAccountTransactionDTO capitalDTO) {
        String xid = context.getXid();
        String freezeKey = "TCC:" + xid + ":capital:" + capitalDTO.getId() + ":freeze";

        if (RedisUtils.hasKey(freezeKey)) {
            return;
        }

        CapitalAccount capitalAccount = capitalAccountRepository.findById(capitalDTO.getId())
            .orElseThrow(() -> new ResourceNotFoundException("capitalAccount", "[id: " + capitalDTO.getId() + "]"));

        BigDecimal freezeAmount = capitalDTO.getAmount();
        if (capitalAccount.getAvailableBalance().compareTo(freezeAmount) < 0) {
            throw new ResourceNotFoundException("capitalAccount", "[id: " + capitalDTO.getId() + "]");
        }

        capitalAccount.setAvailableBalance(
            freezeAmount.compareTo(BigDecimal.ZERO) == 1 ? 
            capitalAccount.getAvailableBalance() : 
            capitalAccount.getAvailableBalance().add(freezeAmount)//neg
        );
        capitalAccount.setFrozenBalance(
            freezeAmount.compareTo(BigDecimal.ZERO) == 1 ? 
            capitalAccount.getFrozenBalance() :
            capitalAccount.getFrozenBalance().subtract(freezeAmount)//neg
        );
        capitalAccountRepository.save(capitalAccount);

        context.put("capitalId", capitalAccount.getId());
        context.put("freezeAmount", freezeAmount); //freezeAmount can be neg
        RedisUtils.set(freezeKey, String.valueOf(freezeAmount));
    }

    @Lock(lockKey = "LOCK:TCC:CAPITAL:{capitalDTO.capitalAccountId}")
    @NoTransactional
    public boolean commitfreezeAmount(TccContext context, CapitalAccountTransactionDTO capitalDTO) {
        String xid = context.getXid();
        String freezeKey = "TCC:" + xid + ":capital:" + capitalDTO.getId() + ":freeze";
        String commitKey = "TCC:" + xid + ":capital:" + capitalDTO.getId() + ":commit";
        String cancelKey = "TCC:" + xid + ":capital:" + capitalDTO.getId() + ":cancel";

        String commitStatus = RedisUtils.get(commitKey);
        if ("COMMITED".equals(commitStatus) || RedisUtils.hasKey(cancelKey)) {
            return true;
        }

        CapitalAccount capitalAccount = capitalAccountRepository.findById(capitalDTO.getId())
            .orElseThrow(() -> new ResourceNotFoundException("capitalAccount", "[id: " + capitalDTO.getId() + "]"));

        capitalAccount.setFrozenBalance(
            capitalDTO.getAmount().compareTo(BigDecimal.ZERO) == 1 ? 
            capitalAccount.getFrozenBalance() :
            capitalAccount.getFrozenBalance().add(capitalDTO.getAmount())//neg
        );
        capitalAccount.setBalance(capitalAccount.getBalance().add(capitalDTO.getAmount()));
        capitalAccountRepository.save(capitalAccount);

        BigDecimal frozenAmount = context.get("freezeAmount", BigDecimal.class)
                                    .add(capitalDTO.getAmount());
        context.put("freezeAmount", frozenAmount);

        if (frozenAmount.equals(BigDecimal.ZERO)) {
            RedisUtils.delete(freezeKey);
            RedisUtils.set(commitKey, "COMMITTED");
            return true;
        } else {
            RedisUtils.set(commitKey, "PENDING");
            return false;
        }
    }

    @Lock(lockKey = "LOCK:TCC:CAPITAL:{capitalDTO.capitalAccountId}")
    @NoTransactional
    public void rollbackfreezeAmount(TccContext context, CapitalAccountTransactionDTO capitalDTO) {
        String xid = context.getXid();
        String freezeKey = "TCC:" + xid + ":capital:" + capitalDTO.getId() + ":freeze";
        String cancelKey = "TCC:" + xid + ":capital:" + capitalDTO.getId() + ":cancel";
    
        if (RedisUtils.hasKey(cancelKey)) {
            return;
        }
    
        CapitalAccount capitalAccount = capitalAccountRepository.findById(capitalDTO.getId())
            .orElseThrow(() -> new ResourceNotFoundException("capitalAccount", "[id: " + capitalDTO.getId() + "]"));
    
        BigDecimal freezeAmount = context.get("freezeAmount", BigDecimal.class);
        capitalAccount.setAvailableBalance(
            freezeAmount.compareTo(BigDecimal.ZERO) == 1 ? 
            capitalAccount.getAvailableBalance() : 
            capitalAccount.getAvailableBalance().subtract(freezeAmount)//neg
        );
        capitalAccount.setFrozenBalance(
            freezeAmount.compareTo(BigDecimal.ZERO) == 1 ? 
            capitalAccount.getFrozenBalance() :
            capitalAccount.getFrozenBalance().add(freezeAmount)
        );
        capitalAccountRepository.save(capitalAccount);
    
        RedisUtils.delete(freezeKey);
        RedisUtils.set(cancelKey, "CANCELED");
    }

}
