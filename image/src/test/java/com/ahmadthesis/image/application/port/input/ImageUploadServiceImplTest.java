package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.application.usecase.ImageUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@SpringBootTest
public class ImageUploadServiceImplTest {
  private ImageUploadService imageUploadService;

  @Mock
  private MultipartFile mockMultipartFile;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    imageUploadService = new ImageUploadServiceImpl();
    ReflectionTestUtils.setField(imageUploadService, "UPLOAD_DIRECTORY", "/test");
  }

  @Test
  @DisplayName("should store image file successfully")
  public void storeImageTest() throws IOException {
    // Arrange
    Mockito.when(mockMultipartFile.getOriginalFilename()).thenReturn("test.jpg");
    Mockito.doNothing().when(mockMultipartFile).transferTo(Mockito.any(Path.class));

    // Act
    Mono<Pair<Boolean, String>> result = imageUploadService.storeImage(mockMultipartFile);

    // Assert
    StepVerifier.create(result)
            .expectNextMatches(resultPair -> resultPair.getFirst().equals(true) && resultPair.getSecond().contains("test.jpg"))
            .verifyComplete();

    Mockito.verify(mockMultipartFile, Mockito.times(1)).getOriginalFilename();
    Mockito.verify(mockMultipartFile, Mockito.times(1)).transferTo(Mockito.any(Path.class));
  }

  @Test
  @DisplayName("should return exception for image storage failed")
  public void testStoreImageIOException() throws IOException {
    // Arrange
    Mockito.when(mockMultipartFile.getOriginalFilename()).thenReturn("test.jpg");
    Mockito.doThrow(new IOException("Test IOException")).when(mockMultipartFile).transferTo(Mockito.any(Path.class));

    // Act
    Mono<Pair<Boolean, String>> result = imageUploadService.storeImage(mockMultipartFile);

    // Assert
    StepVerifier.create(result)
            .expectNextMatches(resultPair -> resultPair.getFirst().equals(false) && resultPair.getSecond().isEmpty())
            .verifyComplete();

    Mockito.verify(mockMultipartFile, Mockito.times(1)).getOriginalFilename();
    Mockito.verify(mockMultipartFile, Mockito.times(1)).transferTo(Mockito.any(Path.class));
  }

  @Test
  @DisplayName("should upload file successfully")
  public void uploadTest() throws IOException {
    // Arrange
    Path tempFilePath = Files.createTempFile("test-upload", ".jpg");
    FilePart file = Mockito.mock(FilePart.class);

    Path tempThumbnailPath = Files.createTempFile("test-upload-thumbnail", ".jpg");
    FilePart thumbnail = Mockito.mock(FilePart.class);

//    Mockito.when(file.filename()).thenReturn("test.jpg");
    Mockito.when(file.transferTo(Mockito.any(Path.class))).thenReturn(Mono.empty());
    Mockito.when(file.content()).thenReturn(Flux.empty());

    Mockito.when(thumbnail.transferTo(Mockito.any(Path.class))).thenReturn(Mono.empty());
    Mockito.when(thumbnail.content()).thenReturn(Flux.empty());

    ImageUploadServiceImpl imageUploadService = new ImageUploadServiceImpl();
    ReflectionTestUtils.setField(imageUploadService, "UPLOAD_DIRECTORY", tempFilePath.getParent().toString());
    ReflectionTestUtils.setField(imageUploadService, "THUMBNAIL_DIRECTORY", tempFilePath.getParent().toString());

    // Act
    Mono<Void> result = imageUploadService.upload(file, thumbnail, "test.jpg");

    // Assert
    StepVerifier.create(result)
            .verifyComplete();

//    Mockito.verify(file, Mockito.times(1)).filename();
    Mockito.verify(file, Mockito.times(1)).transferTo(Mockito.any(Path.class));

    // Clean up
    Files.deleteIfExists(tempFilePath);
  }
}
