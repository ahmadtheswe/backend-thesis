package com.ahmadthesis.payment.usecase;

import com.ahmadthesis.payment.business.Package;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PackageRetriever {
  Flux<Package> getAllPackage();
  Mono<Package> getPackageByName(String packageName);
}
