package com.haydenshui.stock.lib.dto.capital;

import lombok.*;
import java.math.BigDecimal;


/**
 * Data Transfer Object (DTO) for CapitalAccount.
 * <p>
 * This class encapsulates the capital account data for external interactions, 
 * ensuring that sensitive information (such as passwords) is not exposed.
 * </p>
 * 
 * <p>
 * The DTO is primarily used to transfer data between different layers of the application, 
 * such as from the service layer to the presentation layer.
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
public class CapitalAccountDTO {

    /**
     * Unique identifier for the capital account.
     */
    private Long id;

    /**
     * Unique capital account number.
     */
    private String capitalAccountNumber;

    /**
     * Type of the capital account (e.g., cash account, margin account).
     */
    private String type;

    /**
     * Total balance of the capital account.
     */
    private BigDecimal balance;

    /**
     * Available balance that can be used for transactions.
     */
    private BigDecimal availableBalance;

    /**
     * Frozen balance that cannot be used for transactions.
     */
    private BigDecimal frozenBalance;

    /**
     * Currency type associated with this capital account.
     */
    private String currency;

    /**
     * Name of the bank associated with this capital account.
     */
    private String bankName;

    /**
     * Unique bank account number linked to this capital account.
     */
    private String bankAccountNumber;

    /**
     * Password associated with this capital account.
     */
    private String password;

    /**
     * Status of the capital account (e.g., ACTIVE, SUSPENDED, CLOSED).
     */
    private String status;

    /**
     * The securities account associated with this capital account.
     */
    private Long securitiesAccountId;

}
