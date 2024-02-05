package com.ahmadthesis.payment.controller;

import java.util.Map;
import reactor.core.publisher.Mono;

public interface PersistPayment {
  Mono<Map<String, String>> saveCharge(TransactionsDTO transactionsDTO);
  Mono<Object> checkCharge(String orderId);

  Mono<ActivePackageDTO> getActivePayment(String userId);
}
