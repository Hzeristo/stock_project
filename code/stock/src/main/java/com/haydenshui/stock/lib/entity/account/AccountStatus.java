package com.haydenshui.stock.lib.entity.account;

public enum AccountStatus {
    ACTIVE("正常"),       // 账户正常使用
    SUSPENDED("冻结"),    // 账户被冻结，不能交易
    CLOSED("关闭");       // 账户已关闭，无法使用

    private final String description;

    AccountStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static AccountStatus fromString(String value) {
        for (AccountStatus status : AccountStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid AccountStatus: " + value);
    }

    public static AccountStatus fromDescription(String desc) {
        for (AccountStatus status : AccountStatus.values()) {
            if (status.description.equals(desc)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching AccountStatus for description: " + desc);
    }

    public boolean isActive() {
        return this == ACTIVE;
    }
}
