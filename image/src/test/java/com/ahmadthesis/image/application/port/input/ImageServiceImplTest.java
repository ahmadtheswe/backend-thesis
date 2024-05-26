package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.ImagePostgreAdapter;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.PreOrderAdapter;
import com.ahmadthesis.image.application.port.output.ImageDatabase;
import com.ahmadthesis.image.application.port.output.PreOrderDatabase;
import com.ahmadthesis.image.application.usecase.ImageService;
import com.ahmadthesis.image.domain.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

@SpringBootTest
public class ImageServiceImplTest {
  @Mock
  private ImageDatabase imageDatabase;

  @Mock
  private PreOrderDatabase preOrderDatabase;

  private ImageService service;

  @BeforeEach
  void setup() {
    imageDatabase = Mockito.mock(ImagePostgreAdapter.class);
    preOrderDatabase = Mockito.mock(PreOrderAdapter.class);
    service = new ImageServiceImpl(imageDatabase, preOrderDatabase);
  }

  @Test
  @DisplayName("should save image data to database")
  void saveImageDataToDatabaseTest() {
    // Arrange
    String id = UUID.randomUUID().toString();
    String uploaderId = UUID.randomUUID().toString();

    Image image = new Image();
    image.setId(id);
    image.setUploaderId(uploaderId);
    image.setTitle("image 1");
    image.setFilename("image_1.jpg");
    image.setOriginalImageDir("d://image//image_1.jpg");
    image.setMediaType("jpg");
    image.setCreatedAt(1691224239866L);
    image.setIsPublic(true);

    // Act
    Mockito.when(imageDatabase.save(image)).thenReturn(Mono.just(image));
    Mono<Image> savedImage = service.save(image);
    Mockito.verify(imageDatabase, Mockito.times(1)).save(image);

    // Assert
    StepVerifier.create(savedImage)
            .expectNext(image)
            .verifyComplete();
  }

  @Test
  @DisplayName("should return image data from database if id exist")
  void getImageByIdSuccessTest() {
    // Arrange
    String id = UUID.randomUUID().toString();
    String uploaderId = UUID.randomUUID().toString();

    Image image = new Image();
    image.setId(id);
    image.setUploaderId(uploaderId);
    image.setTitle("image 1");
    image.setFilename("image_1.jpg");
    image.setOriginalImageDir("d://image//image_1.jpg");
    image.setMediaType("jpg");
    image.setCreatedAt(1691224239866L);
    image.setIsPublic(true);

    // Act
    Mockito.when(imageDatabase.getImageById(id)).thenReturn(Mono.just(image));
    Mono<Image> foundImage = service.getImageById(id);

    // Assert
    StepVerifier.create(foundImage)
            .expectNext(image)
            .verifyComplete();
  }

  @Test
  @DisplayName("should not return image data from database if id not exist")
  void getImageByIdFailedTest() {
    // Arrange
    String id = UUID.randomUUID().toString();

    // Act
    Mockito.when(imageDatabase.getImageById(id)).thenReturn(Mono.empty());
    Mono<Image> notFoundImage = service.getImageById(id);

    // Assert
    StepVerifier.create(notFoundImage)
            .expectComplete()
            .verify();
  }
}
