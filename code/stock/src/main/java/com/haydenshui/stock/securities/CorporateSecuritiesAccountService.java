package com.haydenshui.stock.securities;

import com.haydenshui.stock.lib.dto.securities.CorporateSecuritiesAccountDTO;
import com.haydenshui.stock.lib.entity.account.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CorporateSecuritiesAccountService implements SecuritiesAccountService<CorporateSecuritiesAccount, CorporateSecuritiesAccountDTO> {
    
    private static final Logger logger = LoggerFactory.getLogger(CorporateSecuritiesAccountService.class);

    private final CorporateSecuritiesAccountRepository repository;

    public CorporateSecuritiesAccountService(CorporateSecuritiesAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<CorporateSecuritiesAccount> createAccount(CorporateSecuritiesAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createAccount'");
    }

    @Override
    public Optional<CorporateSecuritiesAccount> getAccountById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccountById'");
    }

    @Override
    public Optional<CorporateSecuritiesAccount> getAccountByAccountNumber(String accountNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccountByAccountNumber'");
    }

    @Override
    public Optional<CorporateSecuritiesAccount> getAccountByIdCardNo(String idCardNo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccountByIdCardNo'");
    }

    @Override
    public Optional<CorporateSecuritiesAccount> updateAccount(CorporateSecuritiesAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAccount'");
    }

    @Override
    public Optional<CorporateSecuritiesAccount> updateAccount(CorporateSecuritiesAccount account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAccount'");
    }

    @Override
    public Optional<CorporateSecuritiesAccount> disableAccount(CorporateSecuritiesAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disableAccount'");
    }

    @Override
    public Optional<CorporateSecuritiesAccount> disableAccount(CorporateSecuritiesAccount account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disableAccount'");
    }

    @Override
    public Optional<CorporateSecuritiesAccount> reportAccountLoss(CorporateSecuritiesAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reportAccountLoss'");
    }

    @Override
    public Optional<CorporateSecuritiesAccount> restoreAccount(CorporateSecuritiesAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restoreAccount'");
    }

    // ======= user interface =======

}
    


