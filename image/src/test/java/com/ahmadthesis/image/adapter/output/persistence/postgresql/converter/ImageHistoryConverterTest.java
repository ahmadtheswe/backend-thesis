package com.ahmadthesis.image.adapter.output.persistence.postgresql.converter;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.ImageHistoryPostgre;
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
    private ImageHistoryConverter converter;
    private final ImageHistory imageHistoryEntity = new ImageHistory();
    private final ImageHistoryPostgre imageHistoryPostgre = new ImageHistoryPostgre();

    @BeforeEach
    void setup() {
        converter = new ImageHistoryConverter();

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
    }

    @Test
    @DisplayName("should convert ImageHistory to ImageHistoryPostgre")
    void convertImageHistoryToImageHistoryPostgre() {
        // Assign

        // Act
        ImageHistoryPostgre converted = converter.convertDomainToAdapter(imageHistoryEntity);

        // Assert
        Assertions.assertEquals(imageHistoryPostgre.getId(), converted.getId());
        Assertions.assertEquals(imageHistoryPostgre.getImageId(), converted.getImageId());
        Assertions.assertEquals(imageHistoryPostgre.getAccessorId(), converted.getAccessorId());
        Assertions.assertEquals(imageHistoryPostgre.getActivity(), converted.getActivity());
        Assertions.assertEquals(imageHistoryPostgre.getCreatedAt(), converted.getCreatedAt());
    }

    @Test
    @DisplayName("should convert ImageHistoryPostgre to ImageHistory")
    void convertImageHistoryPostgreToImageHistory() {
        // Assign

        // Act
        ImageHistory converted = converter.convertAdapterToDomain(imageHistoryPostgre);

        // Assert
        Assertions.assertEquals(imageHistoryEntity.getId(), converted.getId());
        Assertions.assertEquals(imageHistoryEntity.getImageId(), converted.getImageId());
        Assertions.assertEquals(imageHistoryEntity.getAccessorId(), converted.getAccessorId());
        Assertions.assertEquals(imageHistoryEntity.getActivity(), converted.getActivity());
        Assertions.assertEquals(imageHistoryEntity.getCreatedAt(), converted.getCreatedAt());
    }
}