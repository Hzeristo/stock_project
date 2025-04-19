package com.haydenshui.stock.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class XidUtils {
    
    private XidUtils() {} // prevent instantiation

    public static String generate(String service, String bizType) {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // yyyyMMdd
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return String.format("%s-%s-%s-%s", service.toUpperCase(), bizType.toUpperCase(), date, uuid);
    }
    
}
