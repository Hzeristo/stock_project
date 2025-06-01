package com.haydenshui.stock.lib.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorCode;

    private Object payload;

    public BusinessException(String message, String errorCode, Object payload) {
        super(message);
        this.errorCode = errorCode;
        this.payload = payload;
    }

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object getPayload() {
        return payload;
    }
    
}
