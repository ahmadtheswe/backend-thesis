package com.ahmadthesis.payment.provider.copernicus_price;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface CopernicusPriceRepository extends R2dbcRepository<CopernicusPriceEntity, String> {
  Mono<CopernicusPriceEntity> getCopernicusPriceEntitiesById(String id);
}
