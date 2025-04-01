package com.haydenshui.stock.lib.dto.trade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeExecutionDTO {
    
    /**
     * The unique identifier for the trade execution.
     */
    private Long id;

    /**
     * The ID of the associated trade order for this trade execution.
     * This is used to link this execution back to the original trade order.
     */
    private Long tradeOrderId;

    /**
     * The ID of the associated securities account for this trade execution.
     * This is the account holding the securities involved in the trade execution.
     */
    private Long securitiesAccountId;

    /**
     * The ID of the associated capital account for this trade execution.
     * This is the account involved in the financial transaction of the trade execution.
     */
    private Long capitalAccountId;

    /**
     * The price at which the trade was executed.
     * This value represents the unit price of the executed securities.
     */
    private BigDecimal executedPrice;

    /**
     * The quantity of securities executed in this trade.
     * This represents the number of units of securities involved in the trade execution.
     */
    private Integer executedQuantity;

    /**
     * The timestamp when the trade execution occurred.
     * This captures the exact time of the execution.
     */
    private LocalDateTime executionTime;

}
