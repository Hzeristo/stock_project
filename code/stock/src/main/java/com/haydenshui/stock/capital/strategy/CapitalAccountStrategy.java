package com.haydenshui.stock.capital.strategy;

import com.haydenshui.stock.lib.dto.CheckDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountTransactionDTO;
import com.haydenshui.stock.lib.entity.account.CapitalAccount;
import com.haydenshui.stock.lib.entity.account.CapitalAccountType;
import com.haydenshui.stock.lib.entity.tcc.TccContext;

public interface CapitalAccountStrategy {
    
    boolean matches(CapitalAccountType type);

    // ======= crud =======

    CapitalAccount createAccount(CapitalAccountDTO dto);

    CapitalAccount getAccountById(Long id);

    CapitalAccount getAccountByAccountNumber(String accountNumber);

    CapitalAccount updateAccount(CapitalAccountDTO dto);

    CapitalAccount disableAccount(CapitalAccountDTO dto);

    // ======= account security =======

    boolean disableValidity(CheckDTO dto);


    CapitalAccount restoreAccount(CapitalAccountDTO dto);

    CapitalAccount reportAccountLoss(CapitalAccountDTO dto);

    // ======= trade (rocketmq) =======

    CapitalAccount deposit(CapitalAccountTransactionDTO dto);

    CapitalAccount withdraw(CapitalAccountTransactionDTO dto);

    void freezeAmount(TccContext context, CapitalAccountTransactionDTO dto);

}
