package com.haydenshui.stock.lib.dto.stock;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
    
    /**
     * The unique identifier for the stock.
     * This is the stock's primary key, used to identify the stock in the system.
     */
    private Long stockId;

    /**
     * The unique stock code.
     * This code is used to identify the stock in the exchange and other financial systems.
     */
    private String stockCode;

    /**
     * The name of the stock.
     * This is the official name of the stock, which may differ from the company name.
     */
    private String stockName;

    /**
     * The name of the company that issues the stock.
     * This is the official name of the company which the stock represents.
     */
    private String companyName;

    /**
     * The exchange where the stock is listed.
     * This indicates whether the stock is listed on a specific exchange, such as SSE or SZSE.
     */
    private String exchange;

    /**
     * The industry to which the company belongs.
     * This categorizes the company based on the type of business it operates in.
     */
    private String industry;

    /**
     * The sector within the industry to which the company belongs.
     * This is a finer classification within the industry, often used for financial analysis.
     */
    private String sector;

    /**
     * The listing date of the stock on the exchange.
     * This indicates when the stock began trading on the exchange.
     */
    private LocalDate listingDate;

    /**
     * The International Securities Identification Number (ISIN) of the stock.
     * This unique code is used internationally to identify securities.
     */
    private String isin;

    /**
     * The status of the stock, represented as a string.
     * The possible statuses include ACTIVE, DELISTED, and SUSPENDED.
     */
    private String status;

}
