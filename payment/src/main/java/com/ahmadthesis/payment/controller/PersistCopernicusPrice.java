package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.controller.dto.CopernicusPriceDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersistCopernicusPrice {
  Mono<Void> updatePrice(CopernicusPriceDTO copernicusPrice);
  Flux<CopernicusPriceDTO> getCopernicusPrices();
  Mono<CopernicusPriceDTO> getCopernicusPriceById(String id);
}
