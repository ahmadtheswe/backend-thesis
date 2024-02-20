package com.ahmadthesis.image.application.port.output;

import reactor.core.publisher.Mono;

public interface PaymentWebClient {
  Mono<String> getActivePackage(String token);
}
