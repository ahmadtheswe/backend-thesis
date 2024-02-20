package com.ahmadthesis.image.application.usecase;

import reactor.core.publisher.Mono;

public interface PaymentWebClientService {
  Mono<String> getActivePackage(String token);
}
