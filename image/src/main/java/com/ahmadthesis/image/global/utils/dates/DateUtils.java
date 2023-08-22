package com.ahmadthesis.image.global.utils.dates;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtils {
    @Value("${time.zone}")
    private String timezone;

    @Value("${time.pattern}")
    private String pattern;

    public Long now() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timezone));
        return zonedDateTime.toInstant().toEpochMilli();
    }

    public String millisecondsToDateString(Long millis) {
        ZonedDateTime zonedDateTime = Instant.ofEpochMilli(millis).atZone(ZoneId.of(timezone));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return zonedDateTime.format(dateTimeFormatter);
    }
}