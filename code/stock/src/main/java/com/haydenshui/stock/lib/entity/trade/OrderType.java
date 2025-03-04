package com.haydenshui.stock.lib.entity.trade;

public enum OrderType {
    BUY("买入"),
    SELL("卖出");

    private final String description;

    OrderType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static OrderType fromString(String value) {
        for (OrderType type : OrderType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid OrderType: " + value);
    }


    public static OrderType fromDescription(String desc) {
        for (OrderType type : OrderType.values()) {
            if (type.description.equals(desc)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No matching OrderType for description: " + desc);
    }

    public static boolean isValid(String value) {
        for (OrderType type : OrderType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
