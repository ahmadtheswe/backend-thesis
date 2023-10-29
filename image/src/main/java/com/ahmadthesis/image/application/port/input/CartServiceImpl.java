package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.application.port.output.CartDatabase;
import com.ahmadthesis.image.application.usecase.CartService;
import com.ahmadthesis.image.domain.image.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
  private final CartDatabase database;
  @Override
  public Mono<Cart> save(Cart cart) {
    return database.save(cart);
  }

  @Override
  public Flux<Cart> getUserChartList(String userId) {
    return database.getUserChart(userId);
  }

  @Override
  public Mono<Void> deleteChart(String userId, String cartId) {
    return database.deleteChart(userId, cartId);
  }
}
