package com.haydenshui.stock.lib.entity.stock;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Represents a stock listed on a securities exchange.
 *
 * <p>This entity contains information about a stock, including its stock code, name, company, exchange, and other relevant details.
 * It is used to model a stock within the system for tracking and analysis purposes.</p>
 * 
 * @author Hzeristo
 * @version 1.0
 * @since 2025-03-04
 */
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

    /**
     * The unique identifier for the stock.
     * This is automatically generated as the primary key for the stock entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The stock's unique code.
     * This is used to identify the stock on the exchange and is indexed for quick search.
     */
    @Column(name = "stock_code", unique = true, nullable = false)
    private String stockCode;

    /**
     * The name of the stock.
     * This is the name by which the stock is commonly known.
     */
    @Column(name = "stock_name", nullable = false)
    private String stockName;

    /**
     * The name of the company that issues the stock.
     * This is the company that the stock represents, typically its full legal name.
     */
    @Column(name = "company_name", nullable = false)
    private String companyName;

    /**
     * The exchange where the stock is listed.
     * Examples: SSE (Shanghai Stock Exchange), SZSE (Shenzhen Stock Exchange).
     */
    @Column(name = "exchange", nullable = false, length = 10)
    private String exchange;

    /**
     * The industry sector to which the company belongs.
     * This helps in classifying the stock based on the type of business the company is involved in.
     */
    @Column(name = "industry")
    private String industry;

    /**
     * The sector within the industry to which the company belongs.
     * This can help further categorize the company based on its specialization.
     */
    @Column(name = "sector")
    private String sector;

    /**
     * The date the stock was listed on the exchange.
     * This marks the official start date of the stock's trading activity.
     */
    @Column(name = "listing_date")
    private LocalDate listingDate;

    /**
     * The International Securities Identification Number (ISIN) of the stock.
     * This is a unique identifier for the stock used internationally, often required for global trading.
     */
    @Column(name = "isin", unique = true, length = 20)
    private String isin;

    /**
     * The status of the stock.
     * This field tracks the current state of the stock, indicating whether it is ACTIVE, DELISTED, or SUSPENDED.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StockStatus status;

}
