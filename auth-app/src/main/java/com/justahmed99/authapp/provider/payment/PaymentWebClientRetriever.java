package com.justahmed99.authapp.provider.payment;

import reactor.core.publisher.Mono;

public interface PaymentWebClientRetriever {
  Mono<String> getActivePackage(String token);
}
