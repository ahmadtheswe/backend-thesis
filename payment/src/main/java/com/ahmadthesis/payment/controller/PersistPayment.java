package com.ahmadthesis.payment.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

public interface PersistPayment {
  Mono<Map<String, String>> saveCharge(TransactionsDTO transactionsDTO);
  Mono<Object> checkCharge(String orderId);
}
