package com.ahmadthesis.image.adapter.output.persistence.postgresql.sql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.ImageEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface R2DBCImageRepository extends ReactiveCrudRepository<ImageEntity, String> {
}
