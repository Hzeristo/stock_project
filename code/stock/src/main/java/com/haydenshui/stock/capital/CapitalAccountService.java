package com.haydenshui.stock.capital;

import org.springframework.stereotype.Service;

import com.haydenshui.stock.capital.strategy.CapitalAccountStrategyFactory;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountTransactionDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalCheckDTO;
import com.haydenshui.stock.lib.entity.account.CapitalAccount;
import com.haydenshui.stock.lib.entity.account.CapitalAccountType;
import com.haydenshui.stock.lib.entity.tcc.TccContext;

@Service
public class CapitalAccountService {
    
    private final CapitalAccountStrategyFactory strategyFactory;

    public CapitalAccountService(CapitalAccountStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public CapitalAccount createAccount(CapitalAccountDTO dto, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).createAccount(dto);
    }

    public CapitalAccount getAccountById(Long id, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).getAccountById(id);
    }

    public CapitalAccount getAccountByAccountNumber(String accountNumber, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).getAccountByAccountNumber(accountNumber);
    }

    
    public boolean disableValidity(CapitalCheckDTO payload, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).disableValidity(payload);
    }

    public CapitalAccount updateAccount(CapitalAccountDTO dto, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).updateAccount(dto);
    }

    public CapitalAccount disableAccount(CapitalAccountDTO dto, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).disableAccount(dto);
    }

    public CapitalAccount restoreAccount(CapitalAccountDTO dto, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).restoreAccount(dto);
    }

    public CapitalAccount reportAccountLoss(CapitalAccountDTO dto, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).reportAccountLoss(dto);
    }

    public CapitalAccount deposit(CapitalAccountTransactionDTO dto, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).deposit(dto);
    }

    public CapitalAccount withdraw(CapitalAccountTransactionDTO dto, CapitalAccountType type) {
        return strategyFactory.getStrategy(type).withdraw(dto);
    }

    public void freezeAmount(TccContext context, CapitalAccountTransactionDTO dto, CapitalAccountType type) {
        strategyFactory.getStrategy(type).freezeAmount(context, dto);
    }

    
}
