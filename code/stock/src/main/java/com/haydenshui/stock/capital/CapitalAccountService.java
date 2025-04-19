package com.haydenshui.stock.capital;

import java.util.Optional;

import org.apache.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.stereotype.Service;

import com.haydenshui.stock.capital.strategy.CapitalAccountStrategyFactory;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountTransactionDTO;
import com.haydenshui.stock.lib.entity.account.CapitalAccount;
import com.haydenshui.stock.lib.entity.account.CapitalAccountType;

@Service
public class CapitalAccountService {
    
    private final CapitalAccountStrategyFactory strategyFactory;

    public CapitalAccountService(CapitalAccountStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public Optional<CapitalAccount> createAccount(CapitalAccountDTO dto, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).createAccount(dto);
    }

    public Optional<CapitalAccount> getAccountById(Long id, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).getAccountById(id);
    }

    public Optional<CapitalAccount> getAccountByAccountNumber(String accountNumber, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).getAccountByAccountNumber(accountNumber);
    }

    public Optional<CapitalAccount> updateAccount(CapitalAccountDTO dto, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).updateAccount(dto);
    }

    public Optional<CapitalAccount> disableAccount(CapitalAccountDTO dto, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).disableAccount(dto);
    }

    public Optional<CapitalAccount> restoreAccount(CapitalAccountDTO dto, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).restoreAccount(dto);
    }

    public Optional<CapitalAccount> reportAccountLoss(CapitalAccountDTO dto, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).reportAccountLoss(dto);
    }

    public Optional<CapitalAccount> deposit(CapitalAccountTransactionDTO dto, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).deposit(dto);
    }

    public Optional<CapitalAccount> withdraw(CapitalAccountTransactionDTO dto, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).withdraw(dto);
    }

    public boolean freezeAmount(BusinessActionContext context, CapitalAccountTransactionDTO dto, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).freezeAmount(context, dto);
    }
    
}
