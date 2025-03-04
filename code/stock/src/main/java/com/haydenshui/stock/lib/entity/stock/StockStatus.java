package com.haydenshui.stock.lib.entity.stock;

public enum StockStatus {
    ACTIVE("正常"),       
    SUSPENDED("暂停交易"),    
    DELISTED("退市");       

    private final String description;

    StockStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static StockStatus fromString(String value) {
        for (StockStatus status : StockStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid StockStatus: " + value);
    }

    public static StockStatus fromDescription(String desc) {
        for (StockStatus status : StockStatus.values()) {
            if (status.description.equals(desc)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching StockStatus for description: " + desc);
    }

    public boolean isActive() {
        return this == ACTIVE;
    }
}
