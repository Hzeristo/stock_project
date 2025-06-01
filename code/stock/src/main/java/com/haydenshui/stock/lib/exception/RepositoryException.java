package com.haydenshui.stock.lib.exception;

public class RepositoryException extends BusinessException {

    public RepositoryException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
    
}
