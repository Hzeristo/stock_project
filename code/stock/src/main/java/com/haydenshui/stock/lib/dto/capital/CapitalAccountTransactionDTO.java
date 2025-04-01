package com.haydenshui.stock.lib.dto.capital;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor  
@AllArgsConstructor
public class CapitalAccountTransactionDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    
    /**
     * Unique identifier for the capital account.
     */
    private Long CapitalAccountId;

    /**
     * Unique capital account number.
     */
    private String capitalAccountNumber;

    /**
     * Type of transaction.
     */
    private String type;

    /**
     * Amount of the transaction.
     */
    private BigDecimal amount;

    /**
     * Time of the transaction.
     */
    private LocalDateTime transactionTime;

}
