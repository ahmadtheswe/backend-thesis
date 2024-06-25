package com.ahmadthesis.image.adapter.output.persistence.postgresql.image;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.converter.PreOrderConverter;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.data.PreOrderEntity;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.sql.PreOrderRepository;
import com.ahmadthesis.image.application.port.output.PreOrderDatabase;
import com.ahmadthesis.image.domain.image.PreOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PreOrderAdapter implements PreOrderDatabase {

  private final PreOrderRepository preOrderRepository;

  @Override
  public Mono<PreOrder> save(PreOrder preOrder) {
    PreOrderEntity preOrderEntity = PreOrderConverter.toEntity(preOrder);
//    preOrderEntity.setIsNew(true);
    return this.preOrderRepository.save(preOrderEntity)
        .map(PreOrderConverter::toDomain);
  }

  @Override
  public Mono<PreOrder> getPreorderById(String preorderId) {
    return this.preOrderRepository.findById(preorderId)
        .map(PreOrderConverter::toDomain);
  }

  @Override
  public Mono<PreOrder> getPreorderByPreorderId(String preorderId) {
    return this.preOrderRepository.getPreOrderEntityByPaymentPreorderId(preorderId)
        .map(PreOrderConverter::toDomain);
  }

  @Override
  public Mono<PreOrder> getPreorderByIdAndRequesterId(String id, String requesterId) {
    return this.preOrderRepository.getPreOrderEntityByIdAndRequesterIdAndIsPaidTrue(id, requesterId)
        .map(PreOrderConverter::toDomain);
  }

  @Override
  public Flux<PreOrder> getPreOrders() {
    return this.preOrderRepository.findAll().map(PreOrderConverter::toDomain);
  }

  @Override
  public Flux<PreOrder> getPreOrderByRequesterId(String userId) {
    return this.preOrderRepository.findAllByRequesterIdAndIsActiveIsTrue(userId)
        .map(PreOrderConverter::toDomain);
  }
}
