package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.converter.PreOrderConverter;
import com.ahmadthesis.image.application.port.output.ImageDatabase;
import com.ahmadthesis.image.application.port.output.PreOrderDatabase;
import com.ahmadthesis.image.application.usecase.ImageService;
import com.ahmadthesis.image.domain.image.Image;
import com.ahmadthesis.image.domain.image.PreOrder;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

  private final ImageDatabase imageDatabase;
  private final PreOrderDatabase preOrderDatabase;

  @Override
  public Mono<Image> save(final Image image) {
    return imageDatabase.save(image);
  }

  @Override
  public Mono<Image> getImageById(final String id) {
    return imageDatabase.getImageById(id);
  }

  @Override
  public Mono<Image> getPublicImageById(String id) {
    return imageDatabase.getPublicImageById(id);
  }

  @Override
  public Flux<Image> getImagesPagination(final Integer size, final Integer page,
      final String sortBy,
      final String title, final BigDecimal latitude, final BigDecimal longitude, Double radius) {
    return imageDatabase.getImages(size, page, sortBy, title, latitude, longitude, radius);
  }

  @Override
  public Flux<Image> getPublicImagesPagination(final Integer size, final Integer page,
      final String sortBy, BigDecimal latitude, BigDecimal longitude, Double radius) {
    return imageDatabase.getPublicImages(size, page, sortBy, latitude, longitude, radius);
  }

  @Override
  public Flux<Image> getUserImagesCollectionPagination(
      final Integer size, final Integer page, final String sortBy, final String ownerId,
      BigDecimal latitude, BigDecimal longitude) {
    return imageDatabase.getUserImagesCollection(size, page, sortBy, ownerId, latitude, longitude);
  }

  @Override
  public Mono<Long> getImagesCount() {
    return imageDatabase.getTotalImagesCount();
  }

  @Override
  public Mono<PreOrder> getPreOrderById(String preorderId) {
    return preOrderDatabase.getPreorderById(preorderId);
  }

  @Override
  public Mono<PreOrder> getPreOrderByPreorderId(String preorderId) {
    return preOrderDatabase.getPreorderByPreorderId(preorderId);
  }

  @Override
  public Mono<PreOrder> getPreOrderByIdAndRequesterId(String id, String requesterId) {
    return preOrderDatabase.getPreorderByIdAndRequesterId(id, requesterId);
  }

  @Override
  public Flux<PreOrder> getPreOrderList(String userId) {
    return preOrderDatabase.getPreOrderByRequesterId(userId);
  }

  @Override
  public Flux<PreOrder> getAllPreOrderList() {
    return preOrderDatabase.getPreOrders();
  }

  @Override
  public Mono<PreOrder> savePreOrder(PreOrder preOrder) {
    if (preOrder.getIsNew()) {
      preOrder.setId(UUID.randomUUID().toString());
    }
    return preOrderDatabase.save(preOrder);
  }
}
