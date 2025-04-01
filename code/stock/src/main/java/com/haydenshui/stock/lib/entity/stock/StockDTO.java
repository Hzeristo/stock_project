package com.haydenshui.stock.lib.entity.stock;

import lombok.*;
import java.time.LocalDate;


/**
 * DTO (Data Transfer Object) for the Stock entity.
 *
 * <p>This DTO is used to transfer stock data between layers or services
 * without exposing the underlying Stock entity details. It contains only
 * the essential information necessary for communication.</p>
 * 
 * @author Hzer
 * @version 1.0
 * @since 2025-03-04
 */
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
    private StockStatus status;

    /**
     * Converts a Stock entity to a StockDTO.
     *
     * @param stock the Stock entity to be converted to a StockDTO
     * @return a StockDTO containing data from the provided Stock entity, or null if the input is null
     */
    public static StockDTO toDTO(Stock stock) {
        if (stock == null) {
            return null;
        }
        return new StockDTO(
            stock.getId(),
            stock.getStockCode(),
            stock.getStockName(),
            stock.getCompanyName(),
            stock.getExchange(),
            stock.getIndustry(),
            stock.getSector(),
            stock.getListingDate(),
            stock.getIsin(),
            stock.getStatus()
        );
    } 

    /**
     * Convert this {@link StockDTO} to a {@link Stock} entity.
     * 
     * <p>
     * This method performs lenient validation by ensuring that essential fields are provided, such as 
     * the account ID, account number, registration date, and status. All other fields will be assigned 
     * default values if they are {@code null}. The method constructs a {@link Stock} 
     * with the provided password and the corresponding values from this DTO. It uses default values for optional fields.
     * </p>
     * 
     * <p>
     * Considering information fields in optional and subject to change, lenient method is used,
     * and there will be no strict method to convert DTO to entity.
     * </p>
     */
    public Stock toEntityLenient() {
        if (this.stockCode == null || this.status == null) {
            throw new IllegalArgumentException("Missing essential fields in lenient mode.");
        }
        return new Stock(
            this.stockId,
            this.stockCode,
            this.stockName == null ? "" : this.stockName,
            this.companyName == null ? "" : this.companyName,
            this.exchange == null ? "" : this.exchange,
            this.industry == null ? "" : this.industry,
            this.sector == null ? "" : this.sector,
            this.listingDate == null ? LocalDate.now() : this.listingDate,
            this.isin == null ? "" : this.isin,
            this.status
        );
    }
}
