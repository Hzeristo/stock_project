package com.haydenshui.stock.lib.entity.account;

/**
 * Enum representing the status of an account.
 * <p>
 * This enum defines three possible account status: 
 * ACTIVE: (normal operation), 
 * SUSPENDED: (frozen, no transactions allowed), and 
 * CLOSED: (account is closed and cannot be used again).
 * </p>
 * 
 * @author Hzeristo
 * @version 1.0
 * @since 2025-03-06
 */
public enum AccountStatus {

    /**
     * ACTIVE: The account is fully operational and can be used normally.
     */
    ACTIVE("Active"),

    /**
     * SUSPENDED: The account is frozen and cannot perform transactions.
     */
    SUSPENDED("Suspended"),

    /**
     * CLOSED: The account has been closed and is no longer usable.
     */
    CLOSED("Closed");

    private final String description;

    /**
     * Constructor to initialize the account status with a description.
     *
     * @param description The textual representation of the account status.
     */
    AccountStatus(String description) {
        this.description = description;
    }

    /**
     * Retrieves the description of the account status.
     *
     * @return The description of the status.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Converts a string value to its corresponding {@link AccountStatus} enum.
     *
     * @param value The name of the account status (case insensitive).
     * @return The matching {@link AccountStatus} enum.
     * @throws IllegalArgumentException If the provided value is invalid.
     */
    public static AccountStatus fromString(String value) {
        for (AccountStatus status : AccountStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid AccountStatus: " + value);
    }

    /**
     * Converts a description string to its corresponding {@link AccountStatus} enum.
     *
     * @param desc The description of the account status.
     * @return The matching {@link AccountStatus} enum.
     * @throws IllegalArgumentException If no matching status is found.
     */
    public static AccountStatus fromDescription(String desc) {
        for (AccountStatus status : AccountStatus.values()) {
            if (status.description.equalsIgnoreCase(desc)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching AccountStatus for description: " + desc);
    }

    /**
     * Checks whether the current account status is {@code ACTIVE}.
     *
     * @return {@code true} if the account status is {@code ACTIVE}, otherwise {@code false}.
     */
    public boolean isActive() {
        return this == ACTIVE;
    }
}
