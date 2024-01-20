package com.ahmadthesis.payment.usecase;

import java.util.Map;
import reactor.core.publisher.Mono;

public interface MidtransRetriever {
  Mono<Map<String, Object>> checkCharge(final String orderId);
}
