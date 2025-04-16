package com.haydenshui.stock.lib.entity.stock;

/**
 * Enum representing the possible statuses of a stock.
 *
 * <p>This enum class is used to represent the current trading status of a stock.
 * The statuses include ACTIVE (normal trading), SUSPENDED (trading is temporarily paused),
 * and DELISTED (the stock has been removed from the exchange).</p>
 * 
 * @author Hzer
 * @version 1.0
 * @since 2025-03-04
 */
public enum StockStatus {

    /**
     * Represents a stock that is actively being traded on the exchange.
     */
    ACTIVE("正常"),

    /**
     * Represents a stock that is temporarily suspended from trading on the exchange.
     */
    SUSPENDED("暂停交易"),

    /**
     * Represents a stock that has been removed or delisted from the exchange.
     */
    DELISTED("退市");

    /**
     * The description of the stock status in Chinese.
     * This is used for displaying user-friendly descriptions of the stock status.
     */
    private final String description;

    /**
     * Constructs a StockStatus enum with the provided description.
     * 
     * @param description the user-friendly description of the stock status
     */
    StockStatus(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the stock status.
     * 
     * @return the description of the stock status
     */
    public String getDescription() {
        return description;
    }

    /**
     * Converts a string value to its corresponding StockStatus.
     * This method is case-insensitive and will match the enum constant name.
     * 
     * @param value the string value representing a stock status
     * @return the corresponding StockStatus
     * @throws IllegalArgumentException if no matching StockStatus is found for the given value
     */
    public static StockStatus fromString(String value) {
        for (StockStatus status : StockStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid StockStatus: " + value);
    }

    /**
     * Converts a description of the stock status to its corresponding StockStatus.
     * This method compares the description value (in Chinese) to find a match.
     * 
     * @param desc the description of the stock status in Chinese
     * @return the corresponding StockStatus
     * @throws IllegalArgumentException if no matching StockStatus is found for the given description
     */
    public static StockStatus fromDescription(String desc) {
        for (StockStatus status : StockStatus.values()) {
            if (status.description.equals(desc)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching StockStatus for description: " + desc);
    }

    /**
     * Checks if the stock status is ACTIVE.
     * 
     * @return true if the stock is active, otherwise false
     */
    public boolean isActive() {
        return this == ACTIVE;
    }
}
