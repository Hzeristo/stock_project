package com.haydenshui.stock.lib.entity.position;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "position_change_log")
public class PositionChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position_id", nullable = false)
    private Long positionId;

    @Column(name = "securities_account_id", nullable = false)
    private Long securitiesAccountId;

    @Column(name = "stock_code", nullable = false)
    private String stockCode;

    @Column(name = "change_quantity", nullable = false)
    private Integer changeQuantity; 

    @Column(name = "price", nullable = false, precision = 18, scale = 2)
    private BigDecimal price;
    
    @Column(name = "reason", nullable = false)
    private String reason; 

    @CreationTimestamp
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;
}
