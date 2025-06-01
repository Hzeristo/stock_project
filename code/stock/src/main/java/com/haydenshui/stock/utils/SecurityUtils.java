package com.haydenshui.stock.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.haydenshui.stock.securities.security.SecuritySecuritiesAccount;

public class SecurityUtils {

    public static Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof SecuritySecuritiesAccount userDetails) {
            return userDetails.getId();  // 获取用户的 ID
        }
        throw new IllegalStateException("Invalid authentication principal");
    }

}
