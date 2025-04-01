package com.haydenshui.stock.lib.entity.trade;

/**
 * Enum representing the different types of orders in a trading system.
 *
 * <p>This enum defines the two primary types of orders in a stock trading system: 
 * BUY (buy order) and SELL (sell order). These types dictate the action associated with a trade.</p>
 * 
 * @author Hzer
 * @version 1.0
 * @since 2025-03-04
 */
public enum OrderType {

    /**
     * A buy order, where the action is to purchase stocks.
     */
    BUY("买入"),

    /**
     * A sell order, where the action is to sell stocks.
     */
    SELL("卖出");

    /**
     * The description of the order type in Chinese.
     * This description is used for displaying user-friendly information about the order type.
     */
    private final String description;

    /**
     * Constructs an OrderType enum with the provided description.
     * 
     * @param description the user-friendly description of the order type (e.g., "买入" for BUY)
     */
    OrderType(String description) {
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
     * Converts a string value to its corresponding OrderType.
     * This method is case-insensitive and will match the enum constant name.
     * 
     * @param value the string value representing an order type
     * @return the corresponding OrderType
     * @throws IllegalArgumentException if no matching OrderType is found for the given value
     */
    public static OrderType fromString(String value) {
        for (OrderType type : OrderType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid OrderType: " + value);
    }

    /**
     * Converts a description of the order type to its corresponding OrderType.
     * This method compares the description value (in Chinese) to find a match.
     * 
     * @param desc the description of the order type in Chinese
     * @return the corresponding OrderType
     * @throws IllegalArgumentException if no matching OrderType is found for the given description
     */
    public static OrderType fromDescription(String desc) {
        for (OrderType type : OrderType.values()) {
            if (type.description.equals(desc)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No matching OrderType for description: " + desc);
    }

    /**
     * Checks if the provided string value is a valid OrderType.
     * 
     * @param value the string value to check
     * @return true if the value is a valid OrderType, otherwise false
     */
    public static boolean isValid(String value) {
        for (OrderType type : OrderType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
