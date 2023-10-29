package com.ahmadthesis.image.adapter.output.persistence.postgresql.sql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.CartEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface R2DBCCartRepository extends ReactiveCrudRepository<CartEntity, String> {
  Mono<CartEntity> findByIdAndUserId(String id, String userId);
  Flux<CartEntity> findAllByUserIdOrderByCreatedAtAsc(String userId);
}
