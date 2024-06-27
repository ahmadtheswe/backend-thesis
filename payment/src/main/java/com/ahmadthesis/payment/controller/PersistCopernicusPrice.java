package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.controller.dto.CopernicusPriceDTO;
import com.ahmadthesis.payment.controller.dto.CopernicusPriceUpdateDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersistCopernicusPrice {
  Mono<Void> updatePrice(CopernicusPriceUpdateDTO copernicusPrice);
  Flux<CopernicusPriceDTO> getCopernicusPrices();
  Mono<CopernicusPriceDTO> getCopernicusPriceById(String id);
}
