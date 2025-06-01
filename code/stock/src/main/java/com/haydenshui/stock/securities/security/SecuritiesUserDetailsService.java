package com.haydenshui.stock.securities.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.haydenshui.stock.lib.entity.account.SecuritiesAccount;
import com.haydenshui.stock.securities.SecuritiesAccountService;

public class SecuritiesUserDetailsService implements UserDetailsService {

    private final SecuritiesAccountService securitiesAccountService;

    SecuritiesUserDetailsService(SecuritiesAccountService securitiesAccountService) {
        this.securitiesAccountService = securitiesAccountService;
    }
    
    @Override
    public SecuritySecuritiesAccount loadUserByUsername(String accNoAndType) {
        if (accNoAndType == null || !accNoAndType.contains(":")) {
            throw new UsernameNotFoundException("illegal user signature: " + accNoAndType);
        }
        
        String[] parts = accNoAndType.split(":", 2);
        String accountNumber = parts[0];
        String type = parts[1];

        SecuritiesAccount account = securitiesAccountService.getAccountByAccountNumber(accountNumber, type);
        return new SecuritySecuritiesAccount(account);
    }
}
