package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.application.port.output.ImageDatabase;
import com.ahmadthesis.image.application.usecase.ImageService;
import com.ahmadthesis.image.domain.image.Image;
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
  public Flux<Image> getImagesPagination(final Integer size, final Integer page, final String sortBy) {
    return database.getImages(size, page, sortBy);
  }

  @Override
  public Flux<Image> getPublicImagesPagination(final Integer size, final  Integer page, final String sortBy) {
    return database.getPublicImages(size, page, sortBy);
  }

  @Override
  public Flux<Image> getUserImagesCollectionPagination(
          final Integer size, final Integer page, final String sortBy, final String ownerId
  ) {
    return database.getUserImagesCollection(size, page, sortBy, ownerId);
  }

  @Override
  public Mono<Long> getImagesCount() {
    return database.getTotalImagesCount();
  }
}
