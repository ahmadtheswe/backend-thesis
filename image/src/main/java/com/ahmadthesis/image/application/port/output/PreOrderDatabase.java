package com.ahmadthesis.image.application.port.output;

import com.ahmadthesis.image.domain.image.PreOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PreOrderDatabase {
  Mono<PreOrder> save (PreOrder preOrder);
  Flux<PreOrder> getPreOrders();
  Flux<PreOrder> getPreOrderByRequesterId(String userId);
}
