package com.ahmadthesis.image.adapter.output.persistence.postgresql.sql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.ImageEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface R2DBCImageRepository extends ReactiveCrudRepository<ImageEntity, String> {
  Flux<ImageEntity> findAllByIsPublicTrue();

  @Query("SELECT * FROM image i where i.id in (SELECT distinct o.image_id FROM ownership o " +
          "WHERE o.owner_id = :ownerId) " +
          "AND i.is_public = true")
  Flux<ImageEntity> findAllByOwnerId(String ownerId);
}
