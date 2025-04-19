package com.haydenshui.stock.lib.entity.account;

import java.util.HashMap;
import java.util.Map;

public enum TransactionType {

    DEPOSIT("Deposit"),
    WITHDRAW("Withdraw"),
    FREEZE("freeze"),
    UNFREEZE("unfreeze");

    private final String description;

    private static final Map<String, TransactionType> STRING_TO_ENUM = new HashMap<>();

    static {
        for (TransactionType type : values()) {
            STRING_TO_ENUM.put(type.name().toLowerCase(), type);
        }
    }

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static TransactionType fromString(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null.");
        }
        TransactionType transactionType = STRING_TO_ENUM.get(type.toLowerCase());
        if (transactionType == null) {
            throw new IllegalArgumentException("Unknown transaction type: " + type);
        }
        return transactionType;
    }

    public boolean isDeposit() {
        return this == DEPOSIT;
    }

    public boolean isWithdraw() {
        return this == WITHDRAW;
    }
}
