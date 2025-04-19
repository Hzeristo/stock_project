package com.haydenshui.stock.lib.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class InvalidStrategyInvocationException extends RuntimeException {

    public InvalidStrategyInvocationException(String message) {
        super(message);
    }

    public InvalidStrategyInvocationException(String method, String strategy, MessageSource messageSource) {
        super(messageSource.getMessage("error.capital.invalidStrategyInvocation", 
              new Object[]{method, strategy},  LocaleContextHolder.getLocale()));
    }
}
