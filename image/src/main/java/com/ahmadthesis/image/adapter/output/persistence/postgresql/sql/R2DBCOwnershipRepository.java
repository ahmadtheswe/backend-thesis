package com.ahmadthesis.image.adapter.output.persistence.postgresql.sql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.OwnershipPostgre;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface R2DBCOwnershipRepository extends ReactiveCrudRepository<OwnershipPostgre, String> {
}
