package com.haydenshui.stock.capital.strategy;

import java.util.Optional;

import org.apache.seata.rm.tcc.api.BusinessActionContext;
import org.apache.seata.rm.tcc.api.BusinessActionContextParameter;
import org.apache.seata.rm.tcc.api.LocalTCC;
import org.apache.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.springframework.stereotype.Component;

import com.haydenshui.stock.capital.CapitalAccountRepository;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountTransactionDTO;
import com.haydenshui.stock.lib.entity.account.CapitalAccount;
import com.haydenshui.stock.lib.entity.account.CapitalAccountType;

@LocalTCC
@Component
public class TradeAccountStrategy implements CapitalAccountStrategy {

    private final CapitalAccountRepository capitalAccountRepository;

    public TradeAccountStrategy(CapitalAccountRepository capitalAccountRepository) {
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
    @TwoPhaseBusinessAction(name = "freezeAmount", commitMethod = "commitfreezeAmount", rollbackMethod = "rollbackfreezeAmount")
    public boolean freezeAmount(BusinessActionContext context, 
            @BusinessActionContextParameter("AccountDTO") CapitalAccountTransactionDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'freezeAmount'");
    }

    public boolean commitfreezeAmount(BusinessActionContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'commitfreezeAmount'");
    }

    public boolean rollbackfreezeAmount(BusinessActionContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rollbackfreezeAmoubt'");
    }
    
}
