package com.ahmadthesis.image.adapter.output.persistence.postgresql.sql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.AddChartEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface R2DBCAddChartRepository extends ReactiveCrudRepository<AddChartEntity, String> {
  Mono<AddChartEntity> findByIdAndUserId(String id, String userId);
  Flux<AddChartEntity> findAllByUserIdOrderByCreatedAtAsc(String userId);
}
