package com.haydenshui.stock.capital.strategy;

import java.util.Optional;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.seata.rm.tcc.api.BusinessActionContext;
import org.apache.seata.rm.tcc.api.BusinessActionContextParameter;
import org.apache.seata.rm.tcc.api.LocalTCC;
import org.apache.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.haydenshui.stock.capital.CapitalAccountRepository;
import com.haydenshui.stock.lib.annotation.NoTransactional;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountTransactionDTO;
import com.haydenshui.stock.lib.entity.account.CapitalAccount;
import com.haydenshui.stock.lib.entity.account.CapitalAccountType;

@LocalTCC
@Component
public class TradeAccountStrategy implements CapitalAccountStrategy {

    private final MessageSource messageSource;

    private final CapitalAccountRepository capitalAccountRepository;

    public TradeAccountStrategy(MessageSource messageSource, CapitalAccountRepository capitalAccountRepository) {
        this.messageSource = messageSource;
        this.capitalAccountRepository = capitalAccountRepository;
    }

    @Override
    public boolean matches(CapitalAccountType type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'matches'");
    }

    @Override
    @Transactional
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
    @Transactional
    public Optional<CapitalAccount> updateAccount(CapitalAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAccount'");
    }

    @Override
    @Transactional
    public Optional<CapitalAccount> disableAccount(CapitalAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disableAccount'");
    }

    @Override
    @Transactional
    public Optional<CapitalAccount> restoreAccount(CapitalAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restoreAccount'");
    }

    @Override
    @Transactional
    public Optional<CapitalAccount> reportAccountLoss(CapitalAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reportAccountLoss'");
    }

    @Override
    @Transactional
    public Optional<CapitalAccount> deposit(CapitalAccountTransactionDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deposit'");
    }

    @Override
    @Transactional
    public Optional<CapitalAccount> withdraw(CapitalAccountTransactionDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withdraw'");
    }

    @Override
    @NoTransactional
    @TwoPhaseBusinessAction(name = "freezeAmount", commitMethod = "commitfreezeAmount", rollbackMethod = "rollbackfreezeAmount")
    public boolean freezeAmount(BusinessActionContext context,
            @BusinessActionContextParameter("AccountDTO") CapitalAccountTransactionDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'freezeAmount'");
    }

    @NoTransactional
    public boolean commitfreezeAmount(BusinessActionContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'commitfreezeAmount'");
    }

    @NoTransactional
    public boolean rollbackfreezeAmount(BusinessActionContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rollbackfreezeAmoubt'");
    }

}
