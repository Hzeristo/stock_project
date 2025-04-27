package com.haydenshui.stock.lib.msg;

public enum TransactionAction {
    
    TRADE_CREATE("create"),
    TRADE_COMMIT("commit"),
    TRADE_CANCEL("cancel"),
    TRADE_ABORT("abort"),
    ;

    private final String action;

    TransactionAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public static TransactionAction fromAction(String action) {
        for (TransactionAction transactionAction : TransactionAction.values()) {
            if (transactionAction.getAction().equals(action)) {
                return transactionAction;
            }
        }
        return null;
    }

    public static boolean isValidAction(String action) {
        for (TransactionAction transactionAction : TransactionAction.values()) {
            if (transactionAction.getAction().equals(action)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCreate(String action) {
        return TRADE_CREATE.getAction().equals(action);
    }

    public static boolean isCommit(String action) {
        return TRADE_COMMIT.getAction().equals(action);
    }

    public static boolean isCancel(String action) {
        return TRADE_CANCEL.getAction().equals(action);
    }

    public static boolean isAbort(String action) {
        return TRADE_ABORT.getAction().equals(action);
    }

    @Override 
    public String toString() {
        return action;
    }

}
