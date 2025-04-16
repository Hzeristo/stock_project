package com.haydenshui.stock.lib.entity.account;

/**
 * Enum representing different types of capital accounts.
 * <p>
 * This enum defines two types of capital accounts used for different financial operations:
 * TRADE: Used for stock, futures, and other financial trading activities.
 * FUND: Used for investment in funds.
 * </p>
 * 
 * <p>
 * The enum also provides utility methods for retrieving an instance from a string or description.
 * </p>
 * 
 * @author YourName
 * @version 1.0
 * @since 2025-03-06
 */
public enum CapitalAccountType {

    /**
     * Trading account, used for stock, futures, and other financial trading.
     */
    TRADE("trade"),

    /**
     * Fund account, used for investment in mutual funds and other fund-related assets.
     */
    FUND("fund");

    /**
     * Description of the capital account type in Chinese.
     */
    private final String description;

    /**
     * Constructor for {@code CapitalAccountType}.
     *
     * @param description The human-readable description of the capital account type.
     */
    CapitalAccountType(String description) {
        this.description = description;
    }

    /**
     * Retrieves the description of the capital account type.
     *
     * @return A string representing the description of the account type.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Converts a string value to a {@code CapitalAccountType} enum instance.
     * <p>
     * This method performs a case-insensitive match on the enum name.
     * </p>
     *
     * @param value The string representation of the enum.
     * @return The corresponding {@code CapitalAccountType} instance.
     * @throws IllegalArgumentException If no matching enum is found.
     */
    public static CapitalAccountType fromString(String value) {
        for (CapitalAccountType type : CapitalAccountType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid CapitalAccountType: " + value);
    }

    /**
     * Converts a description to a {@code CapitalAccountType} enum instance.
     * <p>
     * This method searches for an enum that matches the given description.
     * </p>
     *
     * @param desc The description of the account type.
     * @return The corresponding {@code CapitalAccountType} instance.
     * @throws IllegalArgumentException If no matching enum is found.
     */
    public static CapitalAccountType fromDescription(String desc) {
        for (CapitalAccountType type : CapitalAccountType.values()) {
            if (type.description.equals(desc)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No matching CapitalAccountType for description: " + desc);
    }

    /**
     * Checks if the current instance represents a trading account.
     *
     * @return {@code true} if the account type is TRADE, otherwise {@code false}.
     */
    public boolean isTradeAccount() {
        return this == TRADE;
    }

    /**
     * Checks if the current instance represents a fund account.
     *
     * @return {@code true} if the account type is FUND, otherwise {@code false}.
     */
    public boolean isFundAccount() {
        return this == FUND;
    }
}
