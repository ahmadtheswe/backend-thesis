package com.ahmadthesis.image.adapter.input.rest.image.v1.converter;

import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.ImageUploadResponse;
import com.ahmadthesis.image.domain.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.server.ServerRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageRestConverterTest {
  private ImageRestConverter imageRestConverter;

  @Value("${directory.image}")
  private String UPLOAD_DIR;

  @Mock
  private ServerRequest mockServerRequest;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    imageRestConverter = new ImageRestConverter();
    ReflectionTestUtils.setField(imageRestConverter, "UPLOAD_DIR", "uploads");
  }

  @Test
  @DisplayName("should convert Image to ImageUploadResponse")
  public void uploadResponseTest() {
    // Arrange
    Image image = Image.builder()
            .id("123")
            .title("Test Image")
            .build();

    // Act
    ImageUploadResponse result = imageRestConverter.generateUploadResponse(image);

    // Assert
    assertEquals("123", result.getId());
    assertEquals("Test Image", result.getTitle());
  }
}