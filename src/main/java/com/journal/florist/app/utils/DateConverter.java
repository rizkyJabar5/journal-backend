package com.journal.florist.app.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateConverter {

    public static String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss z";

    public static LocalDateTime toLocalDate(Date dateToConvert) {

        ZoneId zoneId = ZoneId.of("Asia/Jakarta");

        return LocalDateTime
                .ofInstant(
                        dateToConvert.toInstant(),
                        zoneId);
    }

    public static DateTimeFormatter formatDate(){

        return DateTimeFormatter
                .ofPattern(DATE_FORMAT)
                .withZone(ZoneId.of("Asia/Jakarta"));
    }
}
