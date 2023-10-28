package com.ahmadthesis.image.adapter.output.persistence.postgresql.converter;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.ImageHistoryEntity;
import com.ahmadthesis.image.domain.entity.image.ImageHistory;
import com.ahmadthesis.image.domain.objectvalue.image.Activity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class ImageHistoryConverterTest {
  private final ImageHistory imageHistory = new ImageHistory();
  private final ImageHistoryEntity imageHistoryEntity = new ImageHistoryEntity();

  @BeforeEach
  void setup() {
    String id = UUID.randomUUID().toString();
    String imageId = UUID.randomUUID().toString();
    String accessorId = UUID.randomUUID().toString();

    imageHistory.setId(id);
    imageHistory.setImageId(imageId);
    imageHistory.setAccessorId(accessorId);
    imageHistory.setActivity(Activity.DOWNLOAD);
    imageHistory.setCreatedAt(1691224239866L);

    imageHistoryEntity.setId(id);
    imageHistoryEntity.setImageId(imageId);
    imageHistoryEntity.setAccessorId(accessorId);
    imageHistoryEntity.setActivity("DOWNLOAD");
    imageHistoryEntity.setCreatedAt(1691224239866L);
  }

  @Test
  @DisplayName("should convert ImageHistory to ImageHistoryPostgre")
  void convertImageHistoryToImageHistoryPostgre() {
    // Assign

    // Act
    ImageHistoryEntity converted = ImageHistoryConverter.convertDomainToEntity(imageHistory);

    // Assert
    Assertions.assertEquals(imageHistory.getId(), converted.getId());
    Assertions.assertEquals(imageHistory.getImageId(), converted.getImageId());
    Assertions.assertEquals(imageHistory.getAccessorId(), converted.getAccessorId());
    Assertions.assertEquals(imageHistory.getActivity().name(), converted.getActivity());
    Assertions.assertEquals(imageHistory.getCreatedAt(), converted.getCreatedAt());
  }

  @Test
  @DisplayName("should convert ImageHistoryPostgre to ImageHistory")
  void convertImageHistoryPostgreToImageHistory() {
    // Assign

    // Act
    ImageHistory converted = ImageHistoryConverter.convertEntityToDomain(imageHistoryEntity);

    // Assert
    Assertions.assertEquals(imageHistory.getId(), converted.getId());
    Assertions.assertEquals(imageHistory.getImageId(), converted.getImageId());
    Assertions.assertEquals(imageHistory.getAccessorId(), converted.getAccessorId());
    Assertions.assertEquals(imageHistory.getActivity(), converted.getActivity());
    Assertions.assertEquals(imageHistory.getCreatedAt(), converted.getCreatedAt());
  }
}