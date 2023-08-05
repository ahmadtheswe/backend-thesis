package com.ahmadthesis.image.adapter.output.persistence.postgresql.sql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.ImagePostgre;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface R2DBCImageRepository extends ReactiveCrudRepository<ImagePostgre, String> {
}
