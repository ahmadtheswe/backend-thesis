package com.ahmadthesis.image.application.port.output;

import com.ahmadthesis.image.domain.image.Cart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartDatabase {
  Mono<Cart> save(final Cart cart);
  Flux<Cart> getUserChart(final String userId);
  Mono<Void> deleteChart(final String userId, final String chartId);
}
