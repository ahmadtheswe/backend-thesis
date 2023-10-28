package com.ahmadthesis.image.application.port.output;

import com.ahmadthesis.image.domain.image.Image;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageDatabase {
  Mono<Image> save(final Image image);

  Mono<Image> getImageById(final String id);

  Flux<Image> getImages(final Integer size, final Integer page, final String sortBy);

  Flux<Image> getPublicImages(final Integer size, final Integer page, final String sortBy);

  Flux<Image> getUserImagesCollection(
          final Integer size, final Integer page, final String sortBy, final String ownerId);

  Mono<Long> getTotalImagesCount();
}
