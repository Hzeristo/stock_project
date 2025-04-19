package com.haydenshui.stock.lib.dto.capital;

import com.haydenshui.stock.lib.entity.account.AccountStatus;
import com.haydenshui.stock.lib.entity.account.CapitalAccount;
import com.haydenshui.stock.lib.entity.account.CapitalAccountTransaction;
import com.haydenshui.stock.lib.entity.account.CapitalAccountType;
import com.haydenshui.stock.lib.entity.account.TransactionType;

import org.springframework.beans.BeanUtils;

public class CapitalMapper {

    private CapitalMapper() {}

    public static CapitalAccountDTO toDTO(CapitalAccount capitalAccount) {
        if(capitalAccount == null) 
            return null;
        CapitalAccountDTO capitalAccountDTO = new CapitalAccountDTO();
        BeanUtils.copyProperties(capitalAccount, capitalAccountDTO);
        capitalAccountDTO.setStatus(capitalAccount.getStatus() != null ? capitalAccount.getStatus().toString() : null);
        capitalAccountDTO.setType(capitalAccount.getType() != null ? capitalAccount.getType().toString() : null);
        return capitalAccountDTO;
    }

    public static CapitalAccountTransactionDTO toDTO(CapitalAccountTransaction capitalAccountTransaction) {
        if(capitalAccountTransaction == null) 
            return null;
        CapitalAccountTransactionDTO capitalAccountTransactionDTO = new CapitalAccountTransactionDTO();
        BeanUtils.copyProperties(capitalAccountTransaction, capitalAccountTransactionDTO);
        capitalAccountTransactionDTO.setType(capitalAccountTransaction.getType() != null ? capitalAccountTransaction.getType().toString() : null);
        return capitalAccountTransactionDTO;
    }

    public static CapitalAccount toEntity(CapitalAccountDTO capitalAccountDTO) {
        if(capitalAccountDTO == null) 
            return null;
        CapitalAccount capitalAccount = new CapitalAccount();
        BeanUtils.copyProperties(capitalAccountDTO, capitalAccount);
        if (capitalAccountDTO.getStatus() != null) {
            capitalAccount.setStatus(AccountStatus.fromString(capitalAccountDTO.getStatus()));
        }
        if (capitalAccountDTO.getType() != null) {
            capitalAccount.setType(CapitalAccountType.fromString(capitalAccountDTO.getType()));
        }
        return capitalAccount;
    }

    public static CapitalAccountTransaction toEntity(CapitalAccountTransactionDTO capitalAccountTransactionDTO) {
        if(capitalAccountTransactionDTO == null) 
            return null;
        CapitalAccountTransaction capitalAccountTransaction = new CapitalAccountTransaction();
        BeanUtils.copyProperties(capitalAccountTransactionDTO, capitalAccountTransaction);
        if (capitalAccountTransactionDTO.getType() != null) {
            capitalAccountTransaction.setType(TransactionType.fromString(capitalAccountTransactionDTO.getType()));
        }
        return capitalAccountTransaction;
    }
}