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
@Table(name = "trade_execution", indexes = {
    @Index(name = "idx_trade_execution_trade_order_id", columnList = "trade_order_id"),
    @Index(name = "idx_trade_execution_securities_account_id", columnList = "securities_account_id"),
    @Index(name = "idx_trade_execution_capital_account_id", columnList = "capital_account_id")
})
public class TradeExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trade_order_id", nullable = false)
    private TradeOrder tradeOrder;

    @ManyToOne(optional = false)
    @JoinColumn(name = "securities_account_id", nullable = false)
    private SecuritiesAccount securitiesAccount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "capital_account_id", nullable = false)
    private CapitalAccount capitalAccount;

    @Column(name = "executed_price", nullable = false, precision = 18, scale = 2)
    private BigDecimal executedPrice;

    @Column(name = "executed_quantity", nullable = false)
    private int executedQuantity;

    @Column(name = "execution_time", nullable = false)
    private Timestamp executionTime;
}