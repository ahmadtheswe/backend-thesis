package com.ahmadthesis.image.adapter.input.rest.image.v1.mapper;

import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.SaveImageRequest;
import com.ahmadthesis.image.domain.entity.image.Image;
import com.ahmadthesis.image.global.utils.dates.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageMapperTest {
    private ImageMapper imageMapper;

    @Mock
    private DateUtils mockDateUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        imageMapper = new ImageMapper(mockDateUtils);
    }

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

        Mockito.when(mockDateUtils.now()).thenReturn(1679827200000L); // Example timestamp

        // Act
        Image result = imageMapper.mapRequestToImage(request);

        // Assert
        assertEquals("Test Image", result.getTitle());
        assertEquals("image/jpeg", result.getMediaType());
        assertEquals("test.jpg", result.getFilename());
        assertEquals("/uploads", result.getOriginalImageDir());
        assertEquals(1679827200000L, result.getCreatedAt());
        assertEquals(true, result.getIsPublic());
    }
}