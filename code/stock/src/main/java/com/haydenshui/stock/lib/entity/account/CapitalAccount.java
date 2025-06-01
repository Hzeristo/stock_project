package com.haydenshui.stock.lib.entity.account;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * Abstract entity representing a capital account.
 * <p>
 * This class defines common attributes and behaviors for different types of capital accounts.
 * It uses a joined inheritance strategy to allow for subclassing while maintaining data normalization.
 * </p>
 * 
 * @author YourName
 * @version 1.0
 * @since 2025-03-06
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "capital_account", indexes = {
    @Index(name = "idx_capital_account_number", columnList = "capital_account_number")
})
public class CapitalAccount {

    /**
     * Unique identifier for the capital account.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique capital account number.
     */
    @Column(name = "capital_account_number", nullable = false, unique = true)
    private String capitalAccountNumber;

    /**
     * Type of the capital account (e.g., cash account, margin account).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CapitalAccountType type;

    /**
     * Total balance of the capital account.
     */
    @Column(name = "balance", nullable = false, precision = 18, scale = 2)
    private BigDecimal balance;

    /**
     * Available balance that can be used for transactions.
     */
    @Column(name = "available_balance", nullable = false, precision = 18, scale = 2)
    private BigDecimal availableBalance;

    /**
     * Frozen balance that cannot be used for transactions.
     */
    @Column(name = "frozen_balance", nullable = false, precision = 18, scale = 2)
    private BigDecimal frozenBalance;

    /**
     * Currency type for this capital account.
     */
    @Column(name = "currency", nullable = false)
    private String currency;

    /**
     * Name of the bank associated with this capital account.
     */
    @Column(name = "bank_name", nullable = false)
    private String bankName;

    /**
     * Unique bank account number linked to this capital account.
     */
    @Column(name = "bank_account_number", nullable = false, unique = true)
    private String bankAccountNumber;

    /**
     * Encrypted password for securing the capital account.
     * <p>
     * The password must be a six-digit numeric value. It is stored as a hashed value using BCrypt.
     * </p>
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Status of the capital account (e.g., ACTIVE, SUSPENDED, CLOSED).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status;

    /**
     * The securities account id associated with this capital account.
     */
    @Column(name = "securities_account_id", nullable = false)
    private Long securitiesAccountId;
}