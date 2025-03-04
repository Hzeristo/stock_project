package com.haydenshui.stock.lib.entity.trade;

public enum OrderKind {
    LIMIT("限价单"),
    MARKET("市价单"),
    STOP("止损单");

    private final String description;

    OrderKind(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static OrderKind fromString(String value) {
        for (OrderKind kind : OrderKind.values()) {
            if (kind.name().equalsIgnoreCase(value)) {
                return kind;
            }
        }
        throw new IllegalArgumentException("Invalid OrderKind: " + value);
    }

    public static OrderKind fromDescription(String desc) {
        for (OrderKind kind : OrderKind.values()) {
            if (kind.description.equals(desc)) {
                return kind;
            }
        }
        throw new IllegalArgumentException("No matching OrderKind for description: " + desc);
    }

    public static boolean isValid(String value) {
        for (OrderKind kind : OrderKind.values()) {
            if (kind.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
