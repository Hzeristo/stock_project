package com.haydenshui.stock.securities.strategy;

import com.haydenshui.stock.lib.dto.CheckDTO;
import com.haydenshui.stock.lib.dto.securities.SecuritiesAccountDTO;
import com.haydenshui.stock.lib.entity.account.SecuritiesAccount;

public interface SecuritiesAccountStrategy<T extends SecuritiesAccount, D extends SecuritiesAccountDTO> {
    
    boolean matches(String type);

    boolean validate(D dto);

    T createAccount(D dto);

    T getAccountById(Long id);

    T getAccountByAccountNumber(String accountNumber);

    T getAccountByIdCardNo(String idCardNo);

    T updateAccount(D dto);

    T updateAccount(T account);

    void tryDisableAccount(D dto);

    void tryDisableAccount(T account);

    T confirmDisableAccount(CheckDTO dto);

    void tryReportAccountLoss(D dto);

    T comfirmAccountLoss(CheckDTO dto);

    T restoreAccount(D dto);


}
