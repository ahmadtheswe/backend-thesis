package com.ahmadthesis.image.global.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public final class DateUtils {

  private static String timezone;
  private static String pattern;

  @Value("${time.zone}")
  public void setTimezone(String timezone) {
    DateUtils.timezone = timezone;
  }

  @Value("${time.pattern}")
  public void setPattern(String pattern) {
    DateUtils.pattern = pattern;
  }

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