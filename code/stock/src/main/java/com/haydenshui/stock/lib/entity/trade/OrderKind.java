package com.haydenshui.stock.lib.entity.trade;

/**
 * Enum representing the different types of orders in a trading system.
 *
 * <p>This enum class defines the types of orders that can be placed in a stock market. 
 * The types include LIMIT (a limit order), MARKET (a market order), and STOP (a stop loss order).</p>
 * 
 * @author Hzer
 * @version 1.0
 * @since 2025-03-04
 */
public enum OrderKind {

    /**
     * Represents a limit order, where the trader specifies the maximum or minimum price
     * at which they are willing to buy or sell a stock.
     */
    LIMIT("限价单"),

    /**
     * Represents a market order, where the trader agrees to buy or sell a stock at the best available price.
     */
    MARKET("市价单"),

    /**
     * Represents a stop loss order, where the trader sets a specific price to sell a stock 
     * if the market price moves unfavorably.
     */
    STOP("止损单");

    /**
     * The description of the order type in Chinese.
     * This is used for displaying user-friendly descriptions of the order kind.
     */
    private final String description;

    /**
     * Constructs an OrderKind enum with the provided description.
     * 
     * @param description the user-friendly description of the order type
     */
    OrderKind(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the order type.
     * 
     * @return the description of the order type
     */
    public String getDescription() {
        return description;
    }

    /**
     * Converts a string value to its corresponding OrderKind.
     * This method is case-insensitive and will match the enum constant name.
     * 
     * @param value the string value representing an order kind
     * @return the corresponding OrderKind
     * @throws IllegalArgumentException if no matching OrderKind is found for the given value
     */
    public static OrderKind fromString(String value) {
        for (OrderKind kind : OrderKind.values()) {
            if (kind.name().equalsIgnoreCase(value)) {
                return kind;
            }
        }
        throw new IllegalArgumentException("Invalid OrderKind: " + value);
    }

    /**
     * Converts a description of the order type to its corresponding OrderKind.
     * This method compares the description value (in Chinese) to find a match.
     * 
     * @param desc the description of the order type in Chinese
     * @return the corresponding OrderKind
     * @throws IllegalArgumentException if no matching OrderKind is found for the given description
     */
    public static OrderKind fromDescription(String desc) {
        for (OrderKind kind : OrderKind.values()) {
            if (kind.description.equals(desc)) {
                return kind;
            }
        }
        throw new IllegalArgumentException("No matching OrderKind for description: " + desc);
    }

    /**
     * Checks if the provided string value is a valid OrderKind.
     * 
     * @param value the string value to check
     * @return true if the value is a valid OrderKind, otherwise false
     */
    public static boolean isValid(String value) {
        for (OrderKind kind : OrderKind.values()) {
            if (kind.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
