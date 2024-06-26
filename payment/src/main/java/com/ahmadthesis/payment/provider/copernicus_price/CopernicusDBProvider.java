package com.ahmadthesis.payment.provider.copernicus_price;

import com.ahmadthesis.payment.business.CopernicusPrice;
import com.ahmadthesis.payment.usecase.CopernicusPricePersister;
import com.ahmadthesis.payment.usecase.CopernicusPriceRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CopernicusDBProvider implements CopernicusPriceRetriever, CopernicusPricePersister {

  private final CopernicusPriceRepository copernicusPriceRepository;

  @Override
  public Mono<Void> updatePrice(CopernicusPrice copernicusPrice) {
    return copernicusPriceRepository.save(CopernicusPriceConverter.toEntity(copernicusPrice, false))
        .then();
  }

  @Override
  public Flux<CopernicusPrice> getCopernicusPrices() {
    return copernicusPriceRepository.findAll()
        .map(CopernicusPriceConverter::toBusiness);
  }

  @Override
  public Mono<CopernicusPrice> getCopernicusPriceById(String id) {
    return copernicusPriceRepository.findById(id)
        .map(CopernicusPriceConverter::toBusiness);
  }
}
