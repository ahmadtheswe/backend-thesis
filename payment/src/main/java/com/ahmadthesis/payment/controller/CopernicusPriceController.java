package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.controller.dto.CopernicusPriceDTO;
import com.ahmadthesis.payment.controller.dto.CopernicusPriceUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("payment/copernicus-price")
@RequiredArgsConstructor
public class CopernicusPriceController {

  private final PersistCopernicusPrice persistCopernicusPrice;

  @GetMapping("")
  Mono<CopernicusPriceDTO> getCopernicusPriceById(@RequestParam(name = "id") String id) {
    return persistCopernicusPrice.getCopernicusPriceById(id);
  }

  @GetMapping("/all")
  Flux<CopernicusPriceDTO> getCopernicusPrices() {
    return persistCopernicusPrice.getCopernicusPrices();
  }

  @PutMapping("/update")
  Mono<Void> updatePrice(@RequestBody CopernicusPriceUpdateDTO copernicusPrice) {
    return persistCopernicusPrice.updatePrice(copernicusPrice);
  }
}
