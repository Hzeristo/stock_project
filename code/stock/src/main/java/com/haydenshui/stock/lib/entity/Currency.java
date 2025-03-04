package com.haydenshui.stock.lib.entity;

public enum Currency {
    CNY("人民币", "¥"),
    USD("美元", "$");

    private final String name;     // 货币名称
    private final String symbol;   // 货币符号

    Currency(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Currency fromString(String value) {
        for (Currency currency : Currency.values()) {
            if (currency.name().equalsIgnoreCase(value)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Invalid Currency: " + value);
    }
}
