package com.ahmadthesis.image.adapter.output.persistence.postgresql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.converter.CartConverter;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.sql.R2DBCCartRepository;
import com.ahmadthesis.image.application.port.output.CartDatabase;
import com.ahmadthesis.image.domain.image.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CartPostgreAdapter implements CartDatabase {
  private final R2DBCCartRepository repository;

  @Override
  public Mono<Cart> save(Cart cart) {
    return repository.save(CartConverter.convertFromDomainToEntity(cart))
            .map(CartConverter::convertFromEntityToDomain);
  }

  @Override
  public Flux<Cart> getUserChart(String userId) {
    return repository.findAllByUserIdOrderByCreatedAtAsc(userId)
            .map(CartConverter::convertFromEntityToDomain);
  }

  @Override
  public Mono<Void> deleteChart(String userId, String chartId) {
    return repository.findByIdAndUserId(chartId, userId)
            .flatMap(entity -> {
              entity.setIsActive(false);
              return repository.save(entity)
                      .then(Mono.empty());
            });
  }
}
