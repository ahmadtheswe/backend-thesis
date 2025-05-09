package com.ahmadthesis.image.application.usecase;

import com.ahmadthesis.image.domain.image.Image;
import com.ahmadthesis.image.domain.image.PreOrder;
import java.math.BigDecimal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageService {

  Mono<Image> save(final Image image);
  Mono<Image> getImageById(final String id);
  Mono<Image> getPublicImageById(final String id);
  Flux<Image> getImagesPagination(final Integer size, final Integer page, final String sortBy,
      String title, BigDecimal latitude, BigDecimal longitude, Double radius);
  Flux<Image> getPublicImagesPagination(final Integer size, final Integer page,
      final String sortBy, BigDecimal latitude, BigDecimal longitude, Double radius);
  Flux<Image> getUserImagesCollectionPagination(
      final Integer size, final Integer page, final String sortBy, final String ownerId,
      BigDecimal latitude, BigDecimal longitude);
  Mono<Long> getImagesCount();
  Mono<PreOrder> getPreOrderById(String preorderId);
  Mono<PreOrder> getPreOrderByPreorderId(String preorderId);
  Mono<PreOrder> getPreOrderByIdAndRequesterId(String id, String requesterId);
  Flux<PreOrder> getPreOrderList(String userId);
  Flux<PreOrder> getAllPreOrderList();
  Mono<PreOrder> savePreOrder(PreOrder preOrder);
}
