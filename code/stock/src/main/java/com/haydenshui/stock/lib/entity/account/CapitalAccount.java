package com.haydenshui.stock.lib.entity.account;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "capital_account", indexes = {
    @Index(name = "idx_capital_account_number", columnList = "capital_account_number")
})
public abstract class CapitalAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capital_account_number", nullable = false, unique = true)
    private String capitalAccountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CapitalAccountType type;

    @Column(name = "balance", nullable = false, precision = 18, scale = 2)
    private BigDecimal balance;

    @Column(name = "available_balance", nullable = false, precision = 18, scale = 2)
    private BigDecimal availableBalance;

    @Column(name = "frozen_balance", nullable = false, precision = 18, scale = 2)
    private BigDecimal frozenBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "bank_account_number", nullable = false, unique = true)
    private String bankAccountNumber;

    @Column(name = "status", nullable = false)
    private AccountStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "securities_account_id", nullable = false)
    private SecuritiesAccount securitiesAccount;
}