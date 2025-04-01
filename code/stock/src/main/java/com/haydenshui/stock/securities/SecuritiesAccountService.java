package com.haydenshui.stock.securities;

import com.haydenshui.stock.lib.dto.securities.SecuritiesAccountDTO;
import com.haydenshui.stock.lib.entity.account.*;

import java.util.Optional;

public interface SecuritiesAccountService<T extends SecuritiesAccount, D extends SecuritiesAccountDTO> {

    Optional<T> createAccount(D dto);

    Optional<T> getAccountById(Long id);

    Optional<T> getAccountByAccountNumber(String accountNumber);

    Optional<T> getAccountByIdCardNo(String idCardNo);

    Optional<T> updateAccount(D dto);

    Optional<T> updateAccount(T account);

    Optional<T> disableAccount(D dto);

    Optional<T> disableAccount(T account);

    Optional<T> reportAccountLoss(D dto);

    Optional<T> restoreAccount(D dto);

}
