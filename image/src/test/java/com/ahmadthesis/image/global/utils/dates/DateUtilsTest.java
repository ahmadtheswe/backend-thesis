package com.ahmadthesis.image.global.utils.dates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DateUtilsTest {
  private DateUtils utils;

  @BeforeEach
  public void setup() {
    utils = new DateUtils();
    ReflectionTestUtils.setField(utils, "timezone", "Asia/Jakarta");
    ReflectionTestUtils.setField(utils, "pattern", "yyyy-MM-dd HH:mm:ss z");
  }

  @Test
  @DisplayName("should return current time in milliseconds")
  public void nowTest() {
    // Arrange
    long currentTimeMillis = System.currentTimeMillis();

    // Act
    Long result = utils.now();

    // Assert
    assertEquals(currentTimeMillis, result, "Current time in milliseconds does not match");
  }

  @Test
  @DisplayName("should convert milliseconds to formatted date string")
  public void millisecondsToDateStringTest() {
    // Arrange
    long millis = 1679827200000L; // Corresponds to "2023-03-26 00:00:00" in the configured timezone
    String expectedDateString = "2023-03-26 17:40:00 WIB";

    // Act
    String result = utils.millisecondsToDateString(millis);

    // Assert
    assertEquals(expectedDateString, result, "Formatted date string does not match");
  }
}