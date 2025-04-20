package com.haydenshui.stock.capital.strategy;

import java.math.BigDecimal;
import java.util.Optional;

import com.haydenshui.stock.lib.dto.capital.CapitalAccountDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountTransactionDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalCheckDTO;
import com.haydenshui.stock.lib.entity.account.CapitalAccount;
import com.haydenshui.stock.lib.entity.account.CapitalAccountType;
import com.haydenshui.stock.lib.entity.tcc.TccContext;
import com.haydenshui.stock.lib.exception.ResourceNotFoundException;

public interface CapitalAccountStrategy {
    
    boolean matches(CapitalAccountType type);

    // ======= crud =======

    CapitalAccount createAccount(CapitalAccountDTO dto);

    CapitalAccount getAccountById(Long id);

    CapitalAccount getAccountByAccountNumber(String accountNumber);

    CapitalAccount updateAccount(CapitalAccountDTO dto);

    CapitalAccount disableAccount(CapitalAccountDTO dto);

    // ======= account security =======

    boolean disableValidity(CapitalCheckDTO dto);

    CapitalAccount restoreAccount(CapitalAccountDTO dto);

    CapitalAccount reportAccountLoss(CapitalAccountDTO dto);

    // ======= trade (rocketmq) =======

    CapitalAccount deposit(CapitalAccountTransactionDTO dto);

    CapitalAccount withdraw(CapitalAccountTransactionDTO dto);

    void freezeAmount(TccContext context, CapitalAccountTransactionDTO dto);

}
