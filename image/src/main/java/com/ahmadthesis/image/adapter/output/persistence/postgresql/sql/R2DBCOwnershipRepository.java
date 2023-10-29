package com.ahmadthesis.image.adapter.output.persistence.postgresql.sql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.OwnershipEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface R2DBCOwnershipRepository extends ReactiveCrudRepository<OwnershipEntity, String> {
  Flux<OwnershipEntity> findAllByOwnerId(String ownerId);
  Mono<OwnershipEntity> findByOwnerIdAndImageId(String ownerId, String userId);
}
