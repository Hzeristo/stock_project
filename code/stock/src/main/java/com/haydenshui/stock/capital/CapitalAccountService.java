package com.haydenshui.stock.capital;

import org.springframework.stereotype.Service;

import com.haydenshui.stock.capital.strategy.CapitalAccountStrategyFactory;
import com.haydenshui.stock.lib.dto.CheckDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountTransactionDTO;
import com.haydenshui.stock.lib.entity.account.CapitalAccount;
import com.haydenshui.stock.lib.entity.account.CapitalAccountType;
import com.haydenshui.stock.lib.entity.tcc.TccContext;

@Service
public class CapitalAccountService {
    
    private final CapitalAccountStrategyFactory strategyFactory;

    public CapitalAccountService(CapitalAccountStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public CapitalAccount createAccount(CapitalAccountDTO dto) {
        return strategyFactory.getStrategy(CapitalAccountType.fromString(dto.getType())).createAccount(dto);
    }

    public CapitalAccount getAccountById(Long id, String type) {
        return strategyFactory.getStrategy(CapitalAccountType.fromString(type)).getAccountById(id);
    }

    public CapitalAccount getAccountByAccountNumber(String accountNumber, String type) {
        return strategyFactory.getStrategy(CapitalAccountType.fromString(type)).getAccountByAccountNumber(accountNumber);
    }

    
    public boolean disableValidity(CheckDTO payload, String type) {
        return strategyFactory.getStrategy(CapitalAccountType.fromString(type)).disableValidity(payload);
    }

    public CapitalAccount updateAccount(CapitalAccountDTO dto) {
        return strategyFactory.getStrategy(CapitalAccountType.fromString(dto.getType())).updateAccount(dto);
    }

    public CapitalAccount disableAccount(CapitalAccountDTO dto) {
        return strategyFactory.getStrategy(CapitalAccountType.fromString(dto.getType())).disableAccount(dto);
    }

    public CapitalAccount restoreAccount(CapitalAccountDTO dto) {
        return strategyFactory.getStrategy(CapitalAccountType.fromString(dto.getType())).restoreAccount(dto);
    }

    public CapitalAccount reportAccountLoss(CapitalAccountDTO dto) {
        return strategyFactory.getStrategy(CapitalAccountType.fromString(dto.getType())).reportAccountLoss(dto);
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
