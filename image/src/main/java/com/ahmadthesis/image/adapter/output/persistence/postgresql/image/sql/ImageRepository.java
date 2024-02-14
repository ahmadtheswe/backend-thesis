package com.ahmadthesis.image.adapter.output.persistence.postgresql.image.sql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.data.ImageEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ImageRepository extends ReactiveCrudRepository<ImageEntity, String> {
  Flux<ImageEntity> findAllByIsPublicTrue();

  @Query("SELECT * FROM image i where i.id in (SELECT distinct o.image_id FROM ownership o " +
          "WHERE o.owner_id = :ownerId) " +
          "AND i.is_public = true")
  Flux<ImageEntity> findAllByOwnerId(String ownerId);

  @Query("SELECT * FROM image i WHERE (:title IS NULL OR LOWER(i.title) "
      + "LIKE CONCAT('%', LOWER(:title), '%'))")
  Flux<ImageEntity> findAllByTitleLikeIgnoreCase(@Param("title") String title);

  Mono<ImageEntity> findByIdAndIsPublicIsTrue(String id);
}
