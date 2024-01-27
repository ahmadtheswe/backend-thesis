package com.ahmadthesis.image.adapter.output.persistence.postgresql.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.ImageEntity;
import com.ahmadthesis.image.domain.image.Image;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImageConverterTest {

  private final Image image = new Image();
  private final ImageEntity imageEntity = new ImageEntity();

  @BeforeEach
  void setUp() {
    String id = UUID.randomUUID().toString();
    String uploaderId = UUID.randomUUID().toString();

    imageEntity.setId(id);
    imageEntity.setUploaderId(uploaderId);
    imageEntity.setTitle("image 1");
    imageEntity.setFilename("image_1.jpg");
    imageEntity.setOriginalImageDir("d://image//image_1.jpg");
    imageEntity.setMediaType("jpg");
    imageEntity.setCreatedAt(1691224239866L);
    imageEntity.setLatestAccess(1691224239866L);
    imageEntity.setIsPublic(true);

    image.setId(id);
    image.setUploaderId(uploaderId);
    image.setTitle("image 1");
    image.setFilename("image_1.jpg");
    image.setOriginalImageDir("d://image//image_1.jpg");
    image.setMediaType("jpg");
    image.setCreatedAt(1691224239866L);
    image.setLatestAccess(1691224239866L);
    image.setIsPublic(true);
  }

  @Test
  @DisplayName("should convert Image to ImagePostgre")
  void convertImageToImagePostgreTest() {
    // Arrange

    // Act
    final ImageEntity converted = ImageConverter.convertDomainToAdapter(image);

    // Assert
    assertEquals(imageEntity.getId(), converted.getId());
    assertEquals(imageEntity.getUploaderId(), converted.getUploaderId());
    assertEquals(imageEntity.getTitle(), converted.getTitle());
    assertEquals(imageEntity.getFilename(), converted.getFilename());
    assertEquals(imageEntity.getOriginalImageDir(), converted.getOriginalImageDir());
    assertEquals(imageEntity.getMediaType(), converted.getMediaType());
    assertEquals(imageEntity.getCreatedAt(), converted.getCreatedAt());
    assertEquals(imageEntity.getLatestAccess(), converted.getLatestAccess());
    assertEquals(imageEntity.getIsPublic(), converted.getIsPublic());

  }

  @Test
  @DisplayName("should convert ImagePostgre to Image")
  void convertImagePostgreToImageTest() {
    // Arrange

    // Act
    Image converted = ImageConverter.convertAdapterToDomain(imageEntity);

    // Assert
    assertEquals(imageEntity.getId(), converted.getId());
    assertEquals(imageEntity.getUploaderId(), converted.getUploaderId());
    assertEquals(imageEntity.getTitle(), converted.getTitle());
    assertEquals(imageEntity.getFilename(), converted.getFilename());
    assertEquals(imageEntity.getOriginalImageDir(), converted.getOriginalImageDir());
    assertEquals(imageEntity.getMediaType(), converted.getMediaType());
    assertEquals(imageEntity.getCreatedAt(), converted.getCreatedAt());
    assertEquals(imageEntity.getLatestAccess(), converted.getLatestAccess());
    assertEquals(imageEntity.getIsPublic(), converted.getIsPublic());
  }
}