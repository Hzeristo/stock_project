package com.haydenshui.stock.lib.dto.trade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeOrderDTO {
    
    /**
     * The unique identifier of the trade order.
     */
    private Long id;

    /**
     * The ID of the associated securities account for this trade order.
     * Only the ID is returned to avoid serialization issues with JPA relationships.
     */
    private Long securitiesAccountId;

    /**
     * The stock code for the security being traded in this order.
     */
    private String stockCode;

    /**
     * The type of the order (e.g., buy or sell).
     * This is stored as a string representation of the order type.
     */
    private String orderType;

    /**
     * The quantity of the stock to be traded in this order.
     */
    private Integer orderQuantity;

    /**
     * The price at which the stock is to be bought or sold in this order.
     */
    private BigDecimal orderPrice;

    /**
     * The validity of the order, specifying how long the order remains active.
     */
    private String orderValidity;

    /**
     * The kind of the order (e.g., market or limit).
     */
    private String orderKind;

    /**
     * The status of the order (e.g., pending, executed, canceled).
     */
    private String orderStatus;

    /**
     * The timestamp when the order was placed.
     * The time is stored as a LocalDateTime, representing the exact time of order creation.
     */
    private LocalDateTime orderTime;
}
