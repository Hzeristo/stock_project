package com.haydenshui.stock.lib.entity.trade;

/**
 * Enum representing the different statuses of an order in a trading system.
 *
 * <p>This enum class defines the various states that an order can have in a stock market system. 
 * The statuses include PENDING (order is pending), EXECUTED (order has been fully executed), 
 * PARTIAL (order has been partially executed), CANCELED (order has been canceled), and FAILED (order has failed).</p>
 * 
 * @author Hzer
 * @version 1.0
 * @since 2025-03-04
 */
public enum OrderStatus {

    /**
     * The order is pending, i.e., it has been placed but has not yet been executed.
     */
    PENDING("挂单中"),

    /**
     * The order is partially trading, i.e., it has been partially executed.
     */
    PARTIALLY_TRADING("部分成交中"),

    /**
     * The order has been fully executed.
     */
    EXECUTED("已成交"),

    /**
     * The order has been partially executed and will not change.
     */
    PARTIALLY_EXECUTED("部分成交"),

    /**
     * The order has been canceled and will not be executed.
     */
    CANCELED("已撤单"),

    /**
     * The order has failed, and no execution has occurred.
     */
    FAILED("失败");

    /**
     * The description of the order status in Chinese.
     * This is used for displaying user-friendly descriptions of the order status.
     */
    private final String description;

    /**
     * Constructs an OrderStatus enum with the provided description.
     * 
     * @param description the user-friendly description of the order status
     */
    OrderStatus(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the order status.
     * 
     * @return the description of the order status
     */
    public String getDescription() {
        return description;
    }

    /**
     * Converts a string value to its corresponding OrderStatus.
     * This method is case-insensitive and will match the enum constant name.
     * 
     * @param value the string value representing an order status
     * @return the corresponding OrderStatus
     * @throws IllegalArgumentException if no matching OrderStatus is found for the given value
     */
    public static OrderStatus fromString(String value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus: " + value);
    }

    /**
     * Converts a description of the order status to its corresponding OrderStatus.
     * This method compares the description value (in Chinese) to find a match.
     * 
     * @param desc the description of the order status in Chinese
     * @return the corresponding OrderStatus
     * @throws IllegalArgumentException if no matching OrderStatus is found for the given description
     */
    public static OrderStatus fromDescription(String desc) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.description.equals(desc)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching OrderStatus for description: " + desc);
    }

    /**
     * Checks if the provided string value is a valid OrderStatus.
     * 
     * @param value the string value to check
     * @return true if the value is a valid OrderStatus, otherwise false
     */
    public static boolean isValid(String value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
