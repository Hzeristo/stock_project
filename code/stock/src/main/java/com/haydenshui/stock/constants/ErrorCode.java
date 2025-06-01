package com.haydenshui.stock.constants;

public enum ErrorCode {

    SUCCESS("0"),

    // db
    INNER_DATABASE_ERROR("1000"),

    // resource
    RESOURCE_NOT_FOUND("2000"),
    RESOURCE_ALREADY_EXISTS("2001"),
    RESOURCE_INSUFFICIENT("2002"),

    // auth（可扩展）

    // illegal use
    INVALID_OPERATION("4000"),
    INVALID_STRATEGY_INVOCATION("4001"),

    // illegal state
    INVALID_STATE("5000"),
    CAPITAL_LEFT("5001"),
    DOUBLE_SUSPENSION("5002"),
    RESTORE_NORMAL("5003"),
    INVALID_ID("5004");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ErrorCode fromCode(String code) {
        for (ErrorCode value : ErrorCode.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
