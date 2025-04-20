package com.haydenshui.stock.capital.strategy;

import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.haydenshui.stock.capital.CapitalAccountRepository;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountDTO;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountTransactionDTO;
import com.haydenshui.stock.lib.entity.account.CapitalAccount;
import com.haydenshui.stock.lib.entity.account.CapitalAccountType;
import com.haydenshui.stock.lib.entity.tcc.TccContext;
import com.haydenshui.stock.lib.exception.InvalidStrategyInvocationException;


@Component
public class FundAccountStrategy extends AbstractCapitalAccountStrategy {

    private final MessageSource messageSource;

    public FundAccountStrategy(MessageSource messageSource, CapitalAccountRepository capitalAccountRepository, PasswordEncoder passwordEncoder) {
        super(capitalAccountRepository, passwordEncoder);
        this.messageSource = messageSource;
    }

    @Override
    public boolean matches(CapitalAccountType type) {
        return type.isFundAccount();
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
    public void freezeAmount(TccContext context, CapitalAccountTransactionDTO dto) {
        throw new InvalidStrategyInvocationException(
            this.getClass().getSimpleName(),
            new Object(){}.getClass().getEnclosingMethod().getName(),
            messageSource
        );
    }
    
}
