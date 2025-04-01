package com.haydenshui.stock.lib.dto.securities;

import org.springframework.beans.BeanUtils;

import com.haydenshui.stock.lib.entity.account.AccountStatus;
import com.haydenshui.stock.lib.entity.account.CorporateSecuritiesAccount;
import com.haydenshui.stock.lib.entity.account.IndividualSecuritiesAccount;

public class SecuritiesMapper {

    private SecuritiesMapper() {}

    public static IndividualSecuritiesAccountDTO toDTO(IndividualSecuritiesAccount individualSecuritiesAccount) {
        if(individualSecuritiesAccount == null)
            return null;
        IndividualSecuritiesAccountDTO individualSecuritiesAccountDTO = new IndividualSecuritiesAccountDTO();
        BeanUtils.copyProperties(individualSecuritiesAccount, individualSecuritiesAccountDTO);
        individualSecuritiesAccountDTO.setStatus(individualSecuritiesAccountDTO.getStatus() != null ? individualSecuritiesAccountDTO.getStatus().toString() : null);
        return individualSecuritiesAccountDTO;
    }

    public static CorporateSecuritiesAccountDTO toDTO(CorporateSecuritiesAccount corporateSecuritiesAccount) {
        if(corporateSecuritiesAccount == null)
            return null;
        CorporateSecuritiesAccountDTO corporateSecuritiesAccountDTO = new CorporateSecuritiesAccountDTO();
        BeanUtils.copyProperties(corporateSecuritiesAccount, corporateSecuritiesAccountDTO);
        corporateSecuritiesAccountDTO.setStatus(corporateSecuritiesAccountDTO.getStatus() != null ? corporateSecuritiesAccountDTO.getStatus().toString() : null);
        return corporateSecuritiesAccountDTO; 
    }
    
    public static IndividualSecuritiesAccount toEntity(IndividualSecuritiesAccountDTO individualSecuritiesAccountDTO) {
        if(individualSecuritiesAccountDTO == null)
            return null;
        IndividualSecuritiesAccount individualSecuritiesAccount = new IndividualSecuritiesAccount();
        BeanUtils.copyProperties(individualSecuritiesAccountDTO, individualSecuritiesAccount);
        if (individualSecuritiesAccount.getStatus() != null) {
            individualSecuritiesAccount.setStatus(AccountStatus.fromString(individualSecuritiesAccountDTO.getStatus()));
        }
        return individualSecuritiesAccount;
    }

    public static CorporateSecuritiesAccount toEntity(CorporateSecuritiesAccountDTO corporateSecuritiesAccountDTO) {
        if(corporateSecuritiesAccountDTO == null)
            return null;
        CorporateSecuritiesAccount corporateSecuritiesAccount = new CorporateSecuritiesAccount();
        BeanUtils.copyProperties(corporateSecuritiesAccountDTO, corporateSecuritiesAccount);
        if (corporateSecuritiesAccount.getStatus() != null) {
            corporateSecuritiesAccount.setStatus(AccountStatus.fromString(corporateSecuritiesAccountDTO.getStatus()));
        }
        return corporateSecuritiesAccount;
    }
}
