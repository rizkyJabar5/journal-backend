package com.journal.florist.app.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateConverter {

    public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm:ss z";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String TIME_FORMAT = "HH:mm:ss z";
    public static ZoneId zoneId = ZoneId.of("Asia/Jakarta");

    public static LocalDateTime toLocalDateTime(Date dateToConvert) {

        return LocalDateTime.ofInstant(
                dateToConvert.toInstant(),
                zoneId);
    }

    public static DateTimeFormatter formatDateTime() {

        return DateTimeFormatter
                .ofPattern(DATETIME_FORMAT)
                .withZone(ZoneId.of("Asia/Jakarta"));
    }
    public static DateTimeFormatter formatDate() {

        return DateTimeFormatter
                .ofPattern(DATE_FORMAT)
                .withZone(ZoneId.of("Asia/Jakarta"));
    }

    public static DateTimeFormatter formatTime() {

        return DateTimeFormatter
                .ofPattern(TIME_FORMAT)
                .withZone(ZoneId.of("Asia/Jakarta"));
    }

    public static LocalDateTime toLocalDate(Date date) {

        return LocalDateTime.ofInstant(date.toInstant(), zoneId);
    }

//    public static LocalDateTime toLocalTime(Date time) {
//        return LocalDateTime.ofInstant(, zoneId);
//    }
}
