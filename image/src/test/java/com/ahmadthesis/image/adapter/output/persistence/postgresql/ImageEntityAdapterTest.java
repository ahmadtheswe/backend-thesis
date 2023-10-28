package com.ahmadthesis.image.adapter.output.persistence.postgresql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.converter.ImageConverter;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.ImageEntity;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.sql.R2DBCImageRepository;
import com.ahmadthesis.image.application.port.output.ImageDatabase;
import com.ahmadthesis.image.domain.entity.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageEntityAdapterTest {
  @InjectMocks
  private ImagePostgreAdapter database;
  @Mock
  R2DBCImageRepository repository;

  @Test
  @DisplayName("should save image data to database")
  void saveImageTest() {
    // Arrange
    String id = UUID.randomUUID().toString();
    String uploaderId = UUID.randomUUID().toString();

    Image imageEntity = new Image();
    imageEntity.setId(id);
    imageEntity.setUploaderId(uploaderId);
    imageEntity.setTitle("image 1");
    imageEntity.setFilename("image_1.jpg");
    imageEntity.setOriginalImageDir("d://image//image_1.jpg");
    imageEntity.setMediaType("jpg");
    imageEntity.setCreatedAt(1691224239866L);
    imageEntity.setLatestAccess(1691224239866L);
    imageEntity.setIsPublic(true);

    ImageEntity imagePostgre = new ImageEntity();
    imagePostgre.setId(id);
    imagePostgre.setUploaderId(uploaderId);
    imagePostgre.setTitle("image 1");
    imagePostgre.setFilename("image_1.jpg");
    imagePostgre.setOriginalImageDir("d://image//image_1.jpg");
    imagePostgre.setMediaType("jpg");
    imagePostgre.setCreatedAt(1691224239866L);
    imagePostgre.setLatestAccess(1691224239866L);
    imagePostgre.setIsPublic(true);

    // Act
    when(repository.save(imagePostgre)).thenReturn(Mono.just(imagePostgre));
    Mono<Image> savedImage = database.save(imageEntity);
    verify(repository, times(1)).save(imagePostgre);

    // Assert
    StepVerifier.create(savedImage)
            .expectNext(imageEntity)
            .verifyComplete();

  }

  @Test
  @DisplayName("should return image from database if exist")
  void getImageByIdTest() {
    // Arrange
    String id = UUID.randomUUID().toString();
    String uploaderId = UUID.randomUUID().toString();

    Image imageEntity = new Image();
    imageEntity.setId(id);
    imageEntity.setUploaderId(uploaderId);
    imageEntity.setTitle("image 1");
    imageEntity.setFilename("image_1.jpg");
    imageEntity.setOriginalImageDir("d://image//image_1.jpg");
    imageEntity.setMediaType("jpg");
    imageEntity.setCreatedAt(1691224239866L);
    imageEntity.setLatestAccess(1691224239866L);
    imageEntity.setIsPublic(true);

    ImageEntity imagePostgre = new ImageEntity();
    imagePostgre.setId(id);
    imagePostgre.setUploaderId(uploaderId);
    imagePostgre.setTitle("image 1");
    imagePostgre.setFilename("image_1.jpg");
    imagePostgre.setOriginalImageDir("d://image//image_1.jpg");
    imagePostgre.setMediaType("jpg");
    imagePostgre.setCreatedAt(1691224239866L);
    imagePostgre.setLatestAccess(1691224239866L);
    imagePostgre.setIsPublic(true);

    // Act
    when(repository.findById(id)).thenReturn(Mono.just(imagePostgre));
    Mono<Image> retrievedImage = database.getImageById(id);

    // Assert
    StepVerifier.create(retrievedImage)
            .expectNext(imageEntity)
            .verifyComplete();
  }

  @Test
  @DisplayName("should return empty from database if not exist")
  void getImageByIdEmptyTest() {
    // Arrange
    String id = UUID.randomUUID().toString();

    // Act
    when(repository.findById(id)).thenReturn(Mono.empty());
    Mono<Image> retrievedImage = database.getImageById(id);

    // Assert
    StepVerifier.create(retrievedImage)
            .expectComplete()
            .verify();
  }
}
