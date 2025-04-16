package com.haydenshui.stock.capital.strategy;

import java.util.Optional;

import org.apache.seata.rm.tcc.api.BusinessActionContext;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountTransactionDTO;
import com.haydenshui.stock.lib.entity.account.CapitalAccount;
import com.haydenshui.stock.lib.entity.account.CapitalAccountType;

public interface CapitalAccountStrategy {
    
    boolean matches(CapitalAccountType type);

    // ======= crud =======

    Optional<CapitalAccount> createAccount(CapitalAccountDTO dto);

    Optional<CapitalAccount> getAccountById(Long id);

    Optional<CapitalAccount> getAccountByAccountNumber(String accountNumber);

    Optional<CapitalAccount> updateAccount(CapitalAccountDTO dto);

    Optional<CapitalAccount> disableAccount(CapitalAccountDTO dto);

    // ======= account security =======

    Optional<CapitalAccount> restoreAccount(CapitalAccountDTO dto);

    Optional<CapitalAccount> reportAccountLoss(CapitalAccountDTO dto);

    // ======= trade (rocketmq) =======

    Optional<CapitalAccount> deposit(CapitalAccountTransactionDTO dto);

    Optional<CapitalAccount> withdraw(CapitalAccountTransactionDTO dto);

    boolean freezeAmount(BusinessActionContext context, CapitalAccountTransactionDTO dto);

}
