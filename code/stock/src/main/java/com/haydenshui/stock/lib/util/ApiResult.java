package com.haydenshui.stock.lib.util;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> {

    private boolean success;

    private boolean error;

    private String message;
    
    private T payload;

    public static <T> ApiResult<T> success(String message, T payload) {
        return new ApiResult<>(true, false, message, payload);
    }

    public static <T> ApiResult<T> failure(String message, T payload) {
        return new ApiResult<>(false, false, message, payload);
    }
    public static <T> ApiResult<T> error(String message, T payload) {
        return new ApiResult<>(false, true, message, payload);
    }
}
