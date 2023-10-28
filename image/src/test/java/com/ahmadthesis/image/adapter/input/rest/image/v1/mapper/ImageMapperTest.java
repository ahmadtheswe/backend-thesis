package com.ahmadthesis.image.adapter.input.rest.image.v1.mapper;

import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.SaveImageRequest;
import com.ahmadthesis.image.domain.image.Image;
import com.ahmadthesis.image.global.utils.dates.DateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ImageMapperTest {
  @Test
  @DisplayName("should map SaveImageRequest to Image")
  public void mapRequestToImageTest() {
    // Arrange
    SaveImageRequest request = SaveImageRequest.builder()
            .title("Test Image")
            .mediaType("image/jpeg")
            .filename("test.jpg")
            .uploadDir("/uploads")
            .isPublic(true)
            .build();

    Mockito.when(DateUtils.now()).thenReturn(1679827200000L); // Example timestamp

    // Act
    Image result = ImageMapper.mapRequestToImage(request);

    // Assert
    assertEquals("Test Image", result.getTitle());
    assertEquals("image/jpeg", result.getMediaType());
    assertEquals("test.jpg", result.getFilename());
    assertEquals("/uploads", result.getOriginalImageDir());
    assertEquals(1679827200000L, result.getCreatedAt());
    assertEquals(true, result.getIsPublic());
  }
}