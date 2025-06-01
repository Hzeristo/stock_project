package com.haydenshui.stock.lib.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.haydenshui.stock.constants.ErrorCode;

public class InvalidStrategyInvocationException extends BusinessException {

    public InvalidStrategyInvocationException(String message) {
        super(message, ErrorCode.INVALID_STRATEGY_INVOCATION.toString());
    }

    public InvalidStrategyInvocationException(String method, String strategy, MessageSource messageSource) {
        super(messageSource.getMessage(
                "error.capital.invalidStrategyInvocation", 
                new Object[]{method, strategy}, 
                LocaleContextHolder.getLocale()), 
            ErrorCode.INVALID_STRATEGY_INVOCATION.toString());
    }
}
