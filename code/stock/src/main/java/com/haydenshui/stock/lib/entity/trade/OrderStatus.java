package com.haydenshui.stock.lib.entity.trade;

public enum OrderStatus {
    PENDING("挂单中"),
    EXECUTED("已成交"),
    PARTIAL("部分成交"),
    CANCELED("已撤单"),
    FAILED("失败");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }

    public static OrderStatus fromString(String value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus: " + value);
    }

    public static OrderStatus fromDescription(String desc) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.description.equals(desc)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching OrderStatus for description: " + desc);
    }

    public static boolean isValid(String value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
