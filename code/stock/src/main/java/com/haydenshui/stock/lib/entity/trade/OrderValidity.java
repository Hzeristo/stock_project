package com.haydenshui.stock.lib.entity.trade;

/**
 * Enum representing the validity types of an order in a trading system.
 *
 * <p>This enum defines the different validity periods for orders, such as DAY_ORDER (valid for the current day).</p>
 * 
 * @author Hzer
 * @version 1.0
 * @since 2025-03-04
 */
public enum OrderValidity {

    /**
     * A day order, which is valid only for the current trading day.
     */
    DAY_ORDER("当日有效");

    /**
     * The description of the order validity type in Chinese.
     * This description is used for displaying user-friendly information about the order validity.
     */
    private final String description;

    /**
     * Constructs an OrderValidity enum with the provided description.
     * 
     * @param description the user-friendly description of the order validity type (e.g., "当日有效" for DAY_ORDER)
     */
    OrderValidity(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the order validity type.
     * 
     * @return the description of the order validity type
     */
    public String getDescription() {
        return description;
    }

    /**
     * Converts a string value to its corresponding OrderValidity.
     * This method is case-insensitive and will match the enum constant name.
     * 
     * @param value the string value representing an order validity type
     * @return the corresponding OrderValidity
     * @throws IllegalArgumentException if no matching OrderValidity is found for the given value
     */
    public static OrderValidity fromString(String value) {
        for (OrderValidity validity : OrderValidity.values()) {
            if (validity.name().equalsIgnoreCase(value)) {
                return validity;
            }
        }
        throw new IllegalArgumentException("Invalid OrderValidity: " + value);
    }

    /**
     * Converts a description of the order validity type to its corresponding OrderValidity.
     * This method compares the description value (in Chinese) to find a match.
     * 
     * @param desc the description of the order validity type in Chinese
     * @return the corresponding OrderValidity
     * @throws IllegalArgumentException if no matching OrderValidity is found for the given description
     */
    public static OrderValidity fromDescription(String desc) {
        for (OrderValidity validity : OrderValidity.values()) {
            if (validity.description.equals(desc)) {
                return validity;
            }
        }
        throw new IllegalArgumentException("No matching OrderValidity for description: " + desc);
    }

    /**
     * Checks if the provided string value is a valid OrderValidity.
     * 
     * @param value the string value to check
     * @return true if the value is a valid OrderValidity, otherwise false
     */
    public static boolean isValid(String value) {
        for (OrderValidity validity : OrderValidity.values()) {
            if (validity.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
