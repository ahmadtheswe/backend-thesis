package com.ahmadthesis.image.application.usecase;

import com.ahmadthesis.image.domain.image.Cart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartService {
  Mono<Cart> save(final Cart cart);
  Flux<Cart> getUserChartList(final String userId);
  Mono<Void> deleteChart(final String userId, final String cartId);
}
