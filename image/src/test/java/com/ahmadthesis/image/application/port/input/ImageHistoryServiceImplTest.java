package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.ImageHistoryPostgreAdapter;
import com.ahmadthesis.image.application.port.output.ImageHistoryDatabase;
import com.ahmadthesis.image.application.usecase.ImageHistoryService;
import com.ahmadthesis.image.domain.image.ImageHistory;
import com.ahmadthesis.image.domain.image.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class ImageHistoryServiceImplTest {
  @Mock
  private ImageHistoryDatabase database;

  private ImageHistoryService service;

  @BeforeEach
  void setup() {
    database = Mockito.mock(ImageHistoryPostgreAdapter.class);
    service = new ImageHistoryServiceImpl(database);
  }

  @Test
  @DisplayName("should save image history to database")
  void saveImageHistoryTest() {
    // Arrange
    ImageHistory image = new ImageHistory();

    String id = UUID.randomUUID().toString();
    String imageId = UUID.randomUUID().toString();
    String accessorId = UUID.randomUUID().toString();

    image.setId(id);
    image.setImageId(imageId);
    image.setAccessorId(accessorId);
    image.setActivity(Activity.DOWNLOAD);
    image.setCreatedAt(1691224239866L);

    // Act
    Mockito.when(database.save(image)).thenReturn(Mono.just(image));
    Mono<ImageHistory> savedImage = service.save(image);
    Mockito.verify(database, Mockito.times(1)).save(image);

    // Assert
    StepVerifier.create(savedImage)
            .expectNext(image)
            .verifyComplete();

  }

  @Test
  @DisplayName("should return image history list from database")
  void getListHistoryByImageIdTest() {
    // Arrange
    String imageId = UUID.randomUUID().toString();
    String accessorId = UUID.randomUUID().toString();

    ImageHistory image1 = new ImageHistory();
    image1.setId(UUID.randomUUID().toString());
    image1.setImageId(imageId);
    image1.setAccessorId(accessorId);
    image1.setActivity(Activity.DOWNLOAD);
    image1.setCreatedAt(1691224239866L);

    ImageHistory image2 = new ImageHistory();
    image2.setId(UUID.randomUUID().toString());
    image2.setImageId(imageId);
    image2.setAccessorId(accessorId);
    image2.setActivity(Activity.DOWNLOAD);
    image2.setCreatedAt(1691224239866L);

    List<ImageHistory> histories = Arrays.asList(image1, image2);

    // Act
    Mockito.when(database.getHistoryByImageId(imageId)).thenReturn(Flux.fromIterable(histories));
    Flux<ImageHistory> imageHistoryFlux = service.getHistoryByImageId(imageId);

    // Assert
    StepVerifier.create(imageHistoryFlux)
            .expectNext(histories.get(0))
            .expectNext(histories.get(1))
            .verifyComplete();

  }

  @Test
  @DisplayName("should not return image history list from database if imageId not found")
  void getListHistoryByImageIdFailedTest() {
    // Arrange
    String imageId = UUID.randomUUID().toString();

    // Act
    Mockito.when(database.getHistoryByImageId(imageId)).thenReturn(Flux.empty());
    Flux<ImageHistory> imageHistoryFlux = service.getHistoryByImageId(imageId);

    // Assert
    StepVerifier.create(imageHistoryFlux)
            .expectComplete()
            .verify();

  }
}
