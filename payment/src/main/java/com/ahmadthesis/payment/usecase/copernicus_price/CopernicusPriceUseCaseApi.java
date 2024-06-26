package com.ahmadthesis.payment.usecase.copernicus_price;

import com.ahmadthesis.payment.controller.CopernicusPriceConverter;
import com.ahmadthesis.payment.controller.PersistCopernicusPrice;
import com.ahmadthesis.payment.controller.dto.CopernicusPriceDTO;
import com.ahmadthesis.payment.usecase.CopernicusPricePersister;
import com.ahmadthesis.payment.usecase.CopernicusPriceRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CopernicusPriceUseCaseApi implements PersistCopernicusPrice {

  private final CopernicusPriceRetriever copernicusPriceRetriever;
  private final CopernicusPricePersister copernicusPricePersister;

  @Override
  public Mono<Void> updatePrice(CopernicusPriceDTO copernicusPrice) {
    return copernicusPricePersister.updatePrice(CopernicusPriceConverter.toBusiness(copernicusPrice));
  }

  @Override
  public Flux<CopernicusPriceDTO> getCopernicusPrices() {
    return copernicusPriceRetriever.getCopernicusPrices().map(CopernicusPriceConverter::toDTO);
  }

  @Override
  public Mono<CopernicusPriceDTO> getCopernicusPriceById(String id) {
    return copernicusPriceRetriever.getCopernicusPriceById(id).map(CopernicusPriceConverter::toDTO);
  }
}
