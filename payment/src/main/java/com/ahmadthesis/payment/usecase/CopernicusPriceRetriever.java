package com.ahmadthesis.payment.usecase;

import com.ahmadthesis.payment.business.CopernicusPrice;
import reactor.core.publisher.Mono;

public interface CopernicusPriceRetriever {
  Mono<CopernicusPrice> getCopernicusPriceById(String id);
}
