package com.ahmadthesis.payment.provider.subpackage;

import com.ahmadthesis.payment.business.Package;
import com.ahmadthesis.payment.usecase.PackageRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PackageDBProvider implements PackageRetriever {

  private final PackageRepository packageRepository;

  @Override
  public Flux<Package> getAllPackage() {
    return packageRepository.findAll().map(PackageConverter::toBusiness);
  }

  @Override
  public Mono<Package> getPackageByName(String packageName) {
    return packageRepository.getPackageEntityByPackageNameAndIsActiveIsTrue(packageName)
        .map(PackageConverter::toBusiness);
  }
}
