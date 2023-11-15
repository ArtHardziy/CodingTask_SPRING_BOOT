package com.expertsoft.phoneshop.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateFunction {
    private DateFunction() {}

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
