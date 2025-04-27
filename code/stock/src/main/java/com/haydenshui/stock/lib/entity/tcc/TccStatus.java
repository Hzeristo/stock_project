package com.haydenshui.stock.lib.entity.tcc;

public enum TccStatus {

    INITIAL("initial"),
    PENDING("pending"),
    CONFIRMED("confirmed"),
    CANCELLED("cancelled");

    private final String description;

    private TccStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static TccStatus fromString(String status) {
        for (TccStatus tccStatus : TccStatus.values()) {
            if (tccStatus.getDescription().equals(status)) {
                return tccStatus;
            }
        }
        return null;
    }

    public static boolean isInitial(String status) {
        return INITIAL.getDescription().equals(status);
    }

    public static boolean isPending(String status) {
        return PENDING.getDescription().equals(status);
    }

    public static boolean isConfirmed(String status) {
        return CONFIRMED.getDescription().equals(status);
    }

    public static boolean isCancelled(String status) {
        return CANCELLED.getDescription().equals(status);
    }
    
}
