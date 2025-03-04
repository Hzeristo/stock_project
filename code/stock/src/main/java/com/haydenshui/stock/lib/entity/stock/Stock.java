package com.haydenshui.stock.lib.entity.stock;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock", indexes = {
    @Index(name = "idx_stock_code", columnList = "stock_code"),
    @Index(name = "idx_exchange", columnList = "exchange")
})
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_code", unique = true, nullable = false, length = 10)
    private String stockCode;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "exchange", nullable = false, length = 10)
    private String exchange; // SSE: 上交所, SZSE: 深交所

    @Column(name = "industry")
    private String industry;

    @Column(name = "sector")
    private String sector;

    @Column(name = "listing_date")
    private LocalDate listingDate;

    @Column(name = "isin", unique = true, length = 20)
    private String isin;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StockStatus status; // ACTIVE, DELISTED, SUSPENDED
}
