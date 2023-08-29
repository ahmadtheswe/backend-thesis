package com.ahmadthesis.image.adapter.output.persistence.postgresql.sql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.ImageHistoryPostgre;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface R2DBCImageHistoryRepository extends ReactiveCrudRepository<ImageHistoryPostgre, String> {
  @Query("SELECT ih.* FROM image_history ih JOIN image i on i.id = ih.image_id WHERE ih.image_id = :imageId")
  Flux<ImageHistoryPostgre> getImageHistoryPostgreByImageId(String imageId);
}
