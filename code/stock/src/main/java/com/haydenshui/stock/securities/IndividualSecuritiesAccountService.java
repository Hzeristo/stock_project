package com.haydenshui.stock.securities;

import com.haydenshui.stock.lib.dto.securities.IndividualSecuritiesAccountDTO;
import com.haydenshui.stock.lib.entity.account.*;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@Service
public class IndividualSecuritiesAccountService implements SecuritiesAccountService<IndividualSecuritiesAccount, IndividualSecuritiesAccountDTO> {

    private final IndividualSecuritiesAccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    public IndividualSecuritiesAccountService(IndividualSecuritiesAccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<IndividualSecuritiesAccount> createAccount(IndividualSecuritiesAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createAccount'");
    }

    @Override
    public Optional<IndividualSecuritiesAccount> getAccountById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccountById'");
    }

    @Override
    public Optional<IndividualSecuritiesAccount> getAccountByAccountNumber(String accountNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccountByAccountNumber'");
    }

    @Override
    public Optional<IndividualSecuritiesAccount> getAccountByIdCardNo(String idCardNo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccountByIdCardNo'");
    }

    @Override
    public Optional<IndividualSecuritiesAccount> updateAccount(IndividualSecuritiesAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAccount'");
    }

    @Override
    public Optional<IndividualSecuritiesAccount> updateAccount(IndividualSecuritiesAccount account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAccount'");
    }

    @Override
    public Optional<IndividualSecuritiesAccount> disableAccount(IndividualSecuritiesAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disableAccount'");
    }

    @Override
    public Optional<IndividualSecuritiesAccount> disableAccount(IndividualSecuritiesAccount account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disableAccount'");
    }

    @Override
    public Optional<IndividualSecuritiesAccount> reportAccountLoss(IndividualSecuritiesAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reportAccountLoss'");
    }

    @Override
    public Optional<IndividualSecuritiesAccount> restoreAccount(IndividualSecuritiesAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restoreAccount'");
    }


}
