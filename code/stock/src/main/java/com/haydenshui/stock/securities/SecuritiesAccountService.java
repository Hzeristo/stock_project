package com.haydenshui.stock.securities;

import org.springframework.stereotype.Service;

import com.haydenshui.stock.lib.dto.CheckDTO;
import com.haydenshui.stock.lib.dto.securities.SecuritiesAccountDTO;
import com.haydenshui.stock.lib.entity.account.*;
import com.haydenshui.stock.securities.strategy.SecuritiesAccountStrategyFactory;

@Service
public class SecuritiesAccountService {

    private final SecuritiesAccountStrategyFactory strategyFactory;

    public SecuritiesAccountService(SecuritiesAccountStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public boolean validate(SecuritiesAccountDTO dto) {
        return strategyFactory.getStrategy(dto.getType()).validate(dto);
    }

    public SecuritiesAccount createAccount(SecuritiesAccountDTO dto) {
        return strategyFactory.getStrategy(dto.getType()).createAccount(dto);
    }

    public SecuritiesAccount getAccountById(Long id, String type) {
        return strategyFactory.getStrategy(type).getAccountById(id);
    }

    public SecuritiesAccount getAccountByAccountNumber(String accountNumber, String type) {
        return strategyFactory.getStrategy(type).getAccountByAccountNumber(accountNumber);
    }

    public SecuritiesAccount getAccountByIdCardNo(String idCardNo, String type) {
        return strategyFactory.getStrategy(type).getAccountByIdCardNo(idCardNo);
    }

    public SecuritiesAccount updateAccount(SecuritiesAccountDTO dto) {
        return strategyFactory.getStrategy(dto.getType()).updateAccount(dto);
    }

    public SecuritiesAccount updateAccount(SecuritiesAccount account) {
        return strategyFactory.getStrategy(account.getType()).updateAccount(account);
    }

    public void tryDisableAccount(SecuritiesAccountDTO dto) {
        strategyFactory.getStrategy(dto.getType()).tryDisableAccount(dto);
    }

    public void tryDisableAccount(SecuritiesAccount account) {
        strategyFactory.getStrategy(account.getType()).tryDisableAccount(account);
    }

    public SecuritiesAccount confirmDisableAccount(CheckDTO dto) {
        return strategyFactory.getStrategy(dto.getType()).confirmDisableAccount(dto);
    }

    public void tryReportAccountLoss(SecuritiesAccountDTO dto) {
        strategyFactory.getStrategy(dto.getType()).tryReportAccountLoss(dto);
    }

    public SecuritiesAccount comfirmAccountLoss(CheckDTO dto) {
        return strategyFactory.getStrategy(dto.getType()).comfirmAccountLoss(dto);
    }

    public SecuritiesAccount restoreAccount(SecuritiesAccountDTO dto) {
        return strategyFactory.getStrategy(dto.getType()).restoreAccount(dto);
    }
}
