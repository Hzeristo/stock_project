package com.haydenshui.stock.lib.entity.trade;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing a trade order in the system.
 *
 * <p>This class is used to map a trade order record in the database, 
 * capturing details about the trade such as the securities account, 
 * stock code, order type, quantity, price, validity, kind, status, 
 * and the time of the order.</p>
 * 
 * @author Hzer
 * @version 1.0
 * @since 2025-03-04
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trade_order", indexes = {
    @Index(name = "idx_securities_account_id", columnList = "securities_account_id"),
    @Index(name = "idx_stock_code", columnList = "stock_code"),
})
public class TradeOrder {

    /**
     * The unique identifier for the trade order.
     * This is the primary key of the table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The ID of the associated securities account for this trade order.
     * This is a foreign key referring to the `securities_account` table.
     */
    @Column(name = "securities_account_id", nullable = false)
    private Long securitiesAccountId;

    /**
     * The stock code for the security being traded.
     * This code represents the unique identifier for the stock in the system.
     */
    @Column(name = "stock_code", nullable = false, length = 10)
    private String stockCode;

    /**
     * The type of the order (e.g., buy or sell).
     * This is an enumerated value that defines whether the order is a buy or sell order.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "order_type", nullable = false)
    private OrderType orderType;

    /**
     * The quantity of the stock to be traded in this order.
     * This indicates the number of units of the stock the user wishes to buy or sell.
     */
    @Column(name = "order_quantity", nullable = false)
    private Integer orderQuantity;

    /**
     * The price at which the stock should be bought or sold in this order.
     * This is the price per unit of the stock.
     */
    @Column(name = "order_price", nullable = false, precision = 18, scale = 2)
    private BigDecimal orderPrice;

    /**
     * The validity of the order, specifying how long the order will remain active.
     * For example, whether the order is valid only for the day or for a longer period.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "order_validity", nullable = false)
    private OrderValidity orderValidity;

    /**
     * The kind of the order (e.g., market or limit).
     * This defines whether the order is a market order, limit order, etc.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "order_kind", nullable = false)
    private OrderKind orderKind;

    /**
     * The status of the order (e.g., pending, executed, canceled).
     * This indicates the current state of the order.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    /**
     * The timestamp when the order was created.
     * This records the exact time when the order was placed in the system.
     */
    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;

}
