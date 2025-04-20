package com.haydenshui.stock.lib.dto.capital;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Data
@Builder
@NoArgsConstructor  
@AllArgsConstructor
public class CapitalAccountTransactionDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;
    
    /**
     * Unique identifier for the capital account.
     */
    private Long securitiesAccountId;

    /**
     * Type of transaction.
     */
    private String type;

    /**
     * Amount of the transaction.
     * sell: positive
     * buy: negative
     */
    private BigDecimal amount;

    /**
     * Time of the transaction.
     */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

}
