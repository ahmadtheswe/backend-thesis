package com.ahmadthesis.image.adapter.output.persistence.postgresql.image.sql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.data.PreOrderEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PreOrderRepository extends R2dbcRepository<PreOrderEntity, String> {
  Flux<PreOrderEntity> findAllByRequesterIdAndIsActiveIsTrue(String userId);
}
