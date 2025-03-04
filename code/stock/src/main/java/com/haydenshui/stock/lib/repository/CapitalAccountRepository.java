package com.haydenshui.stock.lib.repository;

import com.haydenshui.stock.lib.entity.account.CapitalAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapitalAccountRepository extends JpaRepository<CapitalAccount, Long> {

    // 根据资金账户号查询
    Optional<CapitalAccount> findByCapitalAccountNumber(String capitalAccountNumber);

    // 根据银行账户号查询
    Optional<CapitalAccount> findByBankAccountNumber(String bankAccountNumber);
}
