package com.ahmadthesis.image.adapter.output.persistence.postgresql.image.sql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.data.ImageHistoryEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ImageHistoryRepository extends ReactiveCrudRepository<ImageHistoryEntity, String> {
  @Query("SELECT ih.* FROM image_history ih JOIN image i on i.id = ih.image_id WHERE ih.image_id = :imageId")
  Flux<ImageHistoryEntity> getImageHistoryPostgreByImageId(String imageId);
}
