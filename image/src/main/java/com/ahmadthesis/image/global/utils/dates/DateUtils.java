package com.ahmadthesis.image.global.utils.dates;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public final class DateUtils {
  @Value("${time.zone}")
  private static String timezone;

  @Value("${time.pattern}")
  private static String pattern;

  public static Long now() {
    ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timezone));
    return zonedDateTime.toInstant().toEpochMilli();
  }

  public static String millisecondsToDateString(Long millis) {
    ZonedDateTime zonedDateTime = Instant.ofEpochMilli(millis).atZone(ZoneId.of(timezone));
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    return zonedDateTime.format(dateTimeFormatter);
  }
}