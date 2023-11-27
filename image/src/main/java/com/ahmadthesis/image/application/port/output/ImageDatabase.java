package com.ahmadthesis.image.application.port.output;

import com.ahmadthesis.image.domain.image.Image;
import java.math.BigDecimal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageDatabase {

  Mono<Image> save(final Image image);

  Mono<Image> getImageById(final String id);

  Mono<Image> getPublicImageById(final String id);

  Flux<Image> getImages(final Integer size, final Integer page, final String sortBy,
      final String title, final BigDecimal latitude, final BigDecimal longitude);

  Flux<Image> getPublicImages(final Integer size, final Integer page, final String sortBy,
      final BigDecimal latitude, final BigDecimal longitude);

  Flux<Image> getUserImagesCollection(
      final Integer size, final Integer page, final String sortBy, final String ownerId,
      BigDecimal latitude, BigDecimal longitude);

  Mono<Long> getTotalImagesCount();
}
