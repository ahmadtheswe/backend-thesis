package com.ahmadthesis.image.application.usecase;

import com.ahmadthesis.image.domain.image.Image;
import java.math.BigDecimal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageService {

  Mono<Image> save(final Image image);

  Mono<Image> getImageById(final String id);

  Flux<Image> getImagesPagination(final Integer size, final Integer page, final String sortBy,
      String title, BigDecimal latitude, BigDecimal longitude);

  Flux<Image> getPublicImagesPagination(final Integer size, final Integer page,
      final String sortBy, BigDecimal latitude, BigDecimal longitude);

  Flux<Image> getUserImagesCollectionPagination(
      final Integer size, final Integer page, final String sortBy, final String ownerId,
      BigDecimal latitude, BigDecimal longitude);

  Mono<Long> getImagesCount();
}
