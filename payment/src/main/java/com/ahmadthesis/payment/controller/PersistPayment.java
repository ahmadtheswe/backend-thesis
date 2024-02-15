package com.ahmadthesis.payment.controller;

import java.util.Map;
import reactor.core.publisher.Mono;

public interface PersistPayment {
  Mono<Map<String, String>> saveCharge(TransactionsDTO transactionsDTO, String userId);
  Mono<Object> checkCharge(String orderId);

  Mono<ActivePackageDTO> getActivePayment(String userId);
}
