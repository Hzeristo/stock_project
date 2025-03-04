package com.haydenshui.stock.lib.entity.trade;

import com.haydenshui.stock.lib.entity.account.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "securities_account_id", nullable = false)
    private SecuritiesAccount securitiesAccount;

    @Column(name = "stock_code", nullable = false, length = 10)
    private String stockCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type", nullable = false)
    private OrderType orderType;

    @Column(name = "order_quantity", nullable = false)
    private int orderQuantity;

    @Column(name = "order_price", nullable = false, precision = 18, scale = 2)
    private BigDecimal orderPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_validity", nullable = false)
    private OrderValidity orderValidity;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_kind", nullable = false)
    private OrderKind orderKind;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "order_time", nullable = false)
    private Timestamp orderTime;
}
