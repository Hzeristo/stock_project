package com.haydenshui.stock.lib.entity.trade;

public enum OrderValidity {
    DAY_ORDER("当日有效"),
    ;

    private final String description;

    OrderValidity(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static OrderValidity fromString(String value) {
        for (OrderValidity validity : OrderValidity.values()) {
            if (validity.name().equalsIgnoreCase(value)) {
                return validity;
            }
        }
        throw new IllegalArgumentException("Invalid OrderValidity: " + value);
    }

    public static OrderValidity fromDescription(String desc) {
        for (OrderValidity validity : OrderValidity.values()) {
            if (validity.description.equals(desc)) {
                return validity;
            }
        }
        throw new IllegalArgumentException("No matching OrderValidity for description: " + desc);
    }

    public static boolean isValid(String value) {
        for (OrderValidity validity : OrderValidity.values()) {
            if (validity.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
