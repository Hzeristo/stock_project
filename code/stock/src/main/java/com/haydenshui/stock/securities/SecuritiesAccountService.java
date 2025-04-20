package com.haydenshui.stock.securities;

import com.haydenshui.stock.lib.dto.securities.SecuritiesAccountDTO;
import com.haydenshui.stock.lib.entity.account.*;

import java.util.Optional;

public interface SecuritiesAccountService<T extends SecuritiesAccount, D extends SecuritiesAccountDTO> {

    T createAccount(D dto);

    T getAccountById(Long id);

    T getAccountByAccountNumber(String accountNumber);

    T getAccountByIdCardNo(String idCardNo);

    T updateAccount(D dto);

    T updateAccount(T account);

    void tryDisableAccount(D dto);

    void tryDisableAccount(T account);

    T reportAccountLoss(D dto);

    T restoreAccount(D dto);

}
