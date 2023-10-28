package com.ahmadthesis.image.adapter.output.persistence.postgresql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.converter.ImageHistoryConverter;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.ImageHistoryEntity;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.sql.R2DBCImageHistoryRepository;
import com.ahmadthesis.image.application.port.output.ImageHistoryDatabase;
import com.ahmadthesis.image.domain.entity.image.ImageHistory;
import com.ahmadthesis.image.domain.objectvalue.image.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageHistoryPostgreAdapterTest {
  @InjectMocks
  private ImageHistoryPostgreAdapter database;

  @Mock
  private R2DBCImageHistoryRepository repository;


  @Test
  @DisplayName("should save image history to database")
  void imageHistorySaveTest() {
    // Arrange
    ImageHistory imageHistoryEntity = new ImageHistory();
    ImageHistoryEntity imageHistoryPostgre = new ImageHistoryEntity();

    String id = UUID.randomUUID().toString();
    String imageId = UUID.randomUUID().toString();
    String accessorId = UUID.randomUUID().toString();

    imageHistoryEntity.setId(id);
    imageHistoryEntity.setImageId(imageId);
    imageHistoryEntity.setAccessorId(accessorId);
    imageHistoryEntity.setActivity(Activity.DOWNLOAD);
    imageHistoryEntity.setCreatedAt(1691224239866L);

    imageHistoryPostgre.setId(id);
    imageHistoryPostgre.setImageId(imageId);
    imageHistoryPostgre.setAccessorId(accessorId);
    imageHistoryPostgre.setActivity("DOWNLOAD");
    imageHistoryPostgre.setCreatedAt(1691224239866L);

    // Act
    when(repository.save(imageHistoryPostgre)).thenReturn(Mono.just(imageHistoryPostgre));
    Mono<ImageHistory> savedImageHistory = database.save(imageHistoryEntity);
    verify(repository, times(1)).save(imageHistoryPostgre);

    // Assert
    StepVerifier.create(savedImageHistory)
            .expectNext(imageHistoryEntity)
            .verifyComplete();
  }

  @Test
  @DisplayName("should return list of history by image id")
  void getImageHistoryListByImageIdTest() {
    // Arrange
    String imageId = UUID.randomUUID().toString();

    ImageHistory imageHistoryEntity1 = new ImageHistory();
    ImageHistoryEntity imageHistoryPostgre1 = new ImageHistoryEntity();

    String id1 = UUID.randomUUID().toString();
    String accessorId = UUID.randomUUID().toString();

    imageHistoryEntity1.setId(id1);
    imageHistoryEntity1.setImageId(imageId);
    imageHistoryEntity1.setAccessorId(accessorId);
    imageHistoryEntity1.setActivity(Activity.DOWNLOAD);
    imageHistoryEntity1.setCreatedAt(1691224239866L);

    imageHistoryPostgre1.setId(id1);
    imageHistoryPostgre1.setImageId(imageId);
    imageHistoryPostgre1.setAccessorId(accessorId);
    imageHistoryPostgre1.setActivity("DOWNLOAD");
    imageHistoryPostgre1.setCreatedAt(1691224239866L);


    ImageHistory imageHistoryEntity2 = new ImageHistory();
    ImageHistoryEntity imageHistoryPostgre2 = new ImageHistoryEntity();

    String id2 = UUID.randomUUID().toString();

    imageHistoryEntity2.setId(id2);
    imageHistoryEntity2.setImageId(imageId);
    imageHistoryEntity2.setAccessorId(accessorId);
    imageHistoryEntity2.setActivity(Activity.DOWNLOAD);
    imageHistoryEntity2.setCreatedAt(1691224239866L);

    imageHistoryPostgre2.setId(id2);
    imageHistoryPostgre2.setImageId(imageId);
    imageHistoryPostgre2.setAccessorId(accessorId);
    imageHistoryPostgre2.setActivity("DOWNLOAD");
    imageHistoryPostgre2.setCreatedAt(1691224239866L);

    List<ImageHistory> imageHistories = Arrays.asList(imageHistoryEntity1, imageHistoryEntity2);
    List<ImageHistoryEntity> imageHistoryEntities = Arrays.asList(imageHistoryPostgre1, imageHistoryPostgre2);

    // Act
    when(repository.getImageHistoryPostgreByImageId(imageId)).thenReturn(Flux.fromIterable(imageHistoryEntities));
    Flux<ImageHistory> imageHistoryFlux = database.getHistoryByImageId(imageId);

    // Assert
    StepVerifier.create(imageHistoryFlux)
            .expectNext(imageHistories.get(0))
            .expectNext(imageHistories.get(1))
            .verifyComplete();
  }
}
