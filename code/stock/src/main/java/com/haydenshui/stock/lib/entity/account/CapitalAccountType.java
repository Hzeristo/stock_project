package com.haydenshui.stock.lib.entity.account;

public enum CapitalAccountType {
    TRADE("交易账户"),   // 用于股票、期货等交易
    FUND("基金账户");   // 用于基金投资

    private final String description;

    CapitalAccountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static CapitalAccountType fromString(String value) {
        for (CapitalAccountType type : CapitalAccountType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid CapitalAccountType: " + value);
    }

    public static CapitalAccountType fromDescription(String desc) {
        for (CapitalAccountType type : CapitalAccountType.values()) {
            if (type.description.equals(desc)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No matching CapitalAccountType for description: " + desc);
    }

    public boolean isTradeAccount() {
        return this == TRADE;
    }

    public boolean isFundAccount() {
        return this == FUND;
    }
}
