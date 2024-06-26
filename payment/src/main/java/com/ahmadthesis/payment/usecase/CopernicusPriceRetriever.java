package com.ahmadthesis.payment.usecase;

import com.ahmadthesis.payment.business.CopernicusPrice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CopernicusPriceRetriever {
  Flux<CopernicusPrice> getCopernicusPrices();
  Mono<CopernicusPrice> getCopernicusPriceById(String id);
}
