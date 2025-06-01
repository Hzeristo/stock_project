package com.haydenshui.stock.securities.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.haydenshui.stock.lib.entity.account.AccountStatus;
import com.haydenshui.stock.lib.entity.account.SecuritiesAccount;

public class SecuritySecuritiesAccount implements UserDetails {
    
    private final SecuritiesAccount securitiesAccount;

    public SecuritySecuritiesAccount(SecuritiesAccount securitiesAccount) {
        this.securitiesAccount = securitiesAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    public Long getId() {
        return securitiesAccount.getId();
    }

    @Override
    public String getPassword() {
        return securitiesAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return securitiesAccount.getAccountNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return AccountStatus.ACTIVE.equals(securitiesAccount.getStatus());
    }
}
