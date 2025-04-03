package com.haydenshui.stock.capital.strategy;

import java.util.Optional;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.haydenshui.stock.capital.CapitalAccountRepository;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountTransactionDTO;
import com.haydenshui.stock.lib.entity.account.CapitalAccount;
import com.haydenshui.stock.lib.entity.account.CapitalAccountType;
import com.haydenshui.stock.lib.exception.InvalidStrategyInvocationException;

@Component
public class FundAccountStrategy implements CapitalAccountStrategy {

    private final MessageSource messageSource;

    private final CapitalAccountRepository capitalAccountRepository;

    public FundAccountStrategy(MessageSource messageSource, CapitalAccountRepository capitalAccountRepository) {
        this.messageSource = messageSource;
        this.capitalAccountRepository = capitalAccountRepository;
    }

    @Override
    public boolean matches(CapitalAccountType type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'matches'");
    }
    
    @Override
    public Optional<CapitalAccount> createAccount(CapitalAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createAccount'");
    }

    @Override
    public Optional<CapitalAccount> getAccountById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccountById'");
    }

    @Override
    public Optional<CapitalAccount> getAccountByAccountNumber(String accountNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccountByAccountNumber'");
    }

    @Override
    public Optional<CapitalAccount> updateAccount(CapitalAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAccount'");
    }

    @Override
    public Optional<CapitalAccount> disableAccount(CapitalAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disableAccount'");
    }

    @Override
    public Optional<CapitalAccount> restoreAccount(CapitalAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restoreAccount'");
    }

    @Override
    public Optional<CapitalAccount> reportAccountLoss(CapitalAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reportAccountLoss'");
    }

    @Override
    public Optional<CapitalAccount> deposit(CapitalAccountTransactionDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deposit'");
    }

    @Override
    public Optional<CapitalAccount> withdraw(CapitalAccountTransactionDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withdraw'");
    }

    @Override
    public boolean freezeAmount(BusinessActionContext context, CapitalAccountTransactionDTO dto) {
        throw new InvalidStrategyInvocationException(
            this.getClass().getSimpleName(),
            new Object(){}.getClass().getEnclosingMethod().getName(),
            messageSource
        );
    }
    
}
