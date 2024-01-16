package com.ahmadthesis.payment.provider;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PackageRepository extends ReactiveCrudRepository<PackageEntity, String> {

  Mono<PackageEntity> getPackageEntityByPackageNameAndIsActiveIsTrue(final String packageName);
}
