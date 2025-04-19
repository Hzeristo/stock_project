package com.haydenshui.stock.capital.strategy;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.haydenshui.stock.capital.CapitalAccountRepository;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountTransactionDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalMapper;
import com.haydenshui.stock.lib.entity.account.CapitalAccount;
import com.haydenshui.stock.lib.entity.account.CapitalAccountType;
import com.haydenshui.stock.lib.exception.InvalidStrategyInvocationException;
import com.haydenshui.stock.lib.exception.ResourceAlreadyExistsException;
import com.haydenshui.stock.lib.exception.ResourceNotFoundException;
import com.haydenshui.stock.utils.BeanCopyUtils;


@Component
public class FundAccountStrategy implements CapitalAccountStrategy {

    private final MessageSource messageSource;

    private final CapitalAccountRepository capitalAccountRepository;

    private final PasswordEncoder passwordEncoder;

    public FundAccountStrategy(MessageSource messageSource, CapitalAccountRepository capitalAccountRepository, PasswordEncoder passwordEncoder) {
        this.messageSource = messageSource;
        this.capitalAccountRepository = capitalAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean matches(CapitalAccountType type) {
        return type.isFundAccount();
    }
    
    @Override
    @Transactional
    public CapitalAccount createAccount(CapitalAccountDTO dto) {
        Optional<CapitalAccount> existingAccount = capitalAccountRepository.findByCapitalAccountNumber(dto.getCapitalAccountNumber());
        existingAccount.ifPresent(acc -> {
            throw new ResourceAlreadyExistsException("Capital", acc.getCapitalAccountNumber());
        });
        CapitalAccount account  = CapitalMapper.toEntity(dto);
        return capitalAccountRepository.save(account);
    }

    @Override
    public CapitalAccount getAccountById(Long id) {
        return capitalAccountRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Capital", "[id: " + id.toString() + "]"));
    }

    @Override
    public CapitalAccount getAccountByAccountNumber(String accountNumber) {
        return capitalAccountRepository.findByCapitalAccountNumber(accountNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Capital", "[accountNumber: " + accountNumber + "]"));
    }

    @Override
    @Transactional
    public CapitalAccount updateAccount(CapitalAccountDTO dto) {
        Optional<CapitalAccount> existingAccount = capitalAccountRepository.findById(dto.getId());
        if (existingAccount.isEmpty()) {
            throw new ResourceNotFoundException("Capital", "[id: " + dto.getId().toString() + "]");
        }
        CapitalAccount existing = existingAccount.get();
        CapitalAccount patch = CapitalMapper.toEntity(dto);
        BeanCopyUtils.copyNonNullProperties(patch, existing);
        existing.setPassword(passwordEncoder.encode(existing.getPassword()));
        return capitalAccountRepository.save(existing);
    }

    @Override
    public CapitalAccount disableAccount(CapitalAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disableAccount'");
    }

    @Override
    public CapitalAccount restoreAccount(CapitalAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restoreAccount'");
    }

    @Override
    public CapitalAccount reportAccountLoss(CapitalAccountDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reportAccountLoss'");
    }

    @Override
    public CapitalAccount deposit(CapitalAccountTransactionDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deposit'");
    }

    @Override
    public CapitalAccount withdraw(CapitalAccountTransactionDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withdraw'");
    }

    @Override
    public boolean freezeAmount(CapitalAccountTransactionDTO dto) {
        throw new InvalidStrategyInvocationException(
            this.getClass().getSimpleName(),
            new Object(){}.getClass().getEnclosingMethod().getName(),
            messageSource
        );
    }
    
}
