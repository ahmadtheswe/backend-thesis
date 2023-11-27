package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.application.port.output.ImageDatabase;
import com.ahmadthesis.image.application.usecase.ImageService;
import com.ahmadthesis.image.domain.image.Image;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
  private final ImageDatabase database;

  @Override
  public Mono<Image> save(final Image image) {
    return database.save(image);
  }

  @Override
  public Mono<Image> getImageById(final String id) {
    return database.getImageById(id);
  }

  @Override
  public Mono<Image> getPublicImageById(String id) {
    return database.getPublicImageById(id);
  }

  @Override
  public Flux<Image> getImagesPagination(final Integer size, final Integer page, final String sortBy,
      final String title, final BigDecimal latitude, final BigDecimal longitude) {
    return database.getImages(size, page, sortBy, title, latitude, longitude);
  }

  @Override
  public Flux<Image> getPublicImagesPagination(final Integer size, final  Integer page,
      final String sortBy, BigDecimal latitude, BigDecimal longitude) {
    return database.getPublicImages(size, page, sortBy, latitude, longitude);
  }

  @Override
  public Flux<Image> getUserImagesCollectionPagination(
          final Integer size, final Integer page, final String sortBy, final String ownerId,
      BigDecimal latitude, BigDecimal longitude) {
    return database.getUserImagesCollection(size, page, sortBy, ownerId, latitude, longitude);
  }

  @Override
  public Mono<Long> getImagesCount() {
    return database.getTotalImagesCount();
  }
}
