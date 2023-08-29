package com.ahmadthesis.image.application.usecase;

import com.ahmadthesis.image.domain.entity.image.Image;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageService {
  Mono<Image> save(Image image);

  Mono<Image> getImageById(String id);

  Flux<Image> getImagesPagination(Integer size, Integer page, String sortBy);

  Mono<Long> getImagesCount();
}
