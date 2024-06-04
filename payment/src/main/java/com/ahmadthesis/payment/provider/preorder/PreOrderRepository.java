package com.ahmadthesis.payment.provider.preorder;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface PreOrderRepository extends R2dbcRepository<PreOrderEntity, String> {
  Mono<PreOrderEntity> getPreOrderEntityById(String id);
}
