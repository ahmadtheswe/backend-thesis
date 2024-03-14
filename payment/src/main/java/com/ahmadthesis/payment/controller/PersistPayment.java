package com.ahmadthesis.payment.controller;

import reactor.core.publisher.Mono;

public interface PersistPayment {
  Mono<ChargeDTO> saveCharge(TransactionsDTO transactionsDTO, String userId);
  Mono<Object> checkCharge(String orderId);
  Mono<ActivePackageDTO> getActivePayment(String userId);
  Mono<PaymentDTO> getOnProgressPayment(String userId);
  Mono<Void> cancelActivePayment(String userId);
}
