package com.ahmadthesis.image.adapter.output.persistence.postgresql.converter;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.ImagePostgre;
import com.ahmadthesis.image.domain.entity.image.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class ImageConverterTest {
    private ImageConverter converter;
    private final Image imageEntity = new Image();
    private final ImagePostgre imagePostgre = new ImagePostgre();
    @BeforeEach
    void setUp() {
        converter = new ImageConverter();

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

        imagePostgre.setId(id);
        imagePostgre.setUploaderId(uploaderId);
        imagePostgre.setTitle("image 1");
        imagePostgre.setFilename("image_1.jpg");
        imagePostgre.setOriginalImageDir("d://image//image_1.jpg");
        imagePostgre.setMediaType("jpg");
        imagePostgre.setCreatedAt(1691224239866L);
        imagePostgre.setLatestAccess(1691224239866L);
        imagePostgre.setIsPublic(true);
    }

    @Test
    @DisplayName("should convert Image to ImagePostgre")
    void convertImageToImagePostgreTest() {
        // Arrange

        // Act
        ImagePostgre converted = converter.convertDomainToAdapter(imageEntity);

        // Assert
        Assertions.assertEquals(imagePostgre.getId(), converted.getId());
        Assertions.assertEquals(imagePostgre.getUploaderId(), converted.getUploaderId());
        Assertions.assertEquals(imagePostgre.getTitle(), converted.getTitle());
        Assertions.assertEquals(imagePostgre.getFilename(), converted.getFilename());
        Assertions.assertEquals(imagePostgre.getOriginalImageDir(), converted.getOriginalImageDir());
        Assertions.assertEquals(imagePostgre.getMediaType(), converted.getMediaType());
        Assertions.assertEquals(imagePostgre.getCreatedAt(), converted.getCreatedAt());
        Assertions.assertEquals(imagePostgre.getLatestAccess(), converted.getLatestAccess());
        Assertions.assertEquals(imagePostgre.getIsPublic(), converted.getIsPublic());
    }

    @Test
    @DisplayName("should convert ImagePostgre to Image")
    void convertImagePostgreToImageTest() {
        // Arrange

        // Act
        Image converted = converter.convertAdapterToDomain(imagePostgre);

        // Assert
        Assertions.assertEquals(imageEntity.getId(), converted.getId());
        Assertions.assertEquals(imageEntity.getUploaderId(), converted.getUploaderId());
        Assertions.assertEquals(imageEntity.getTitle(), converted.getTitle());
        Assertions.assertEquals(imageEntity.getFilename(), converted.getFilename());
        Assertions.assertEquals(imageEntity.getOriginalImageDir(), converted.getOriginalImageDir());
        Assertions.assertEquals(imageEntity.getMediaType(), converted.getMediaType());
        Assertions.assertEquals(imageEntity.getCreatedAt(), converted.getCreatedAt());
        Assertions.assertEquals(imageEntity.getLatestAccess(), converted.getLatestAccess());
        Assertions.assertEquals(imageEntity.getIsPublic(), converted.getIsPublic());
    }
}