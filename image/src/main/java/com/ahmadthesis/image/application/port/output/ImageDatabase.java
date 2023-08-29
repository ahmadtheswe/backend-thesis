package com.ahmadthesis.image.application.port.output;

import com.ahmadthesis.image.domain.entity.image.Image;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageDatabase {
  Mono<Image> save(Image image);

  Mono<Image> getImageById(String id);

  Flux<Image> getImages(Integer size, Integer page, String sortBy);

  Mono<Long> getTotalImagesCount();
}
