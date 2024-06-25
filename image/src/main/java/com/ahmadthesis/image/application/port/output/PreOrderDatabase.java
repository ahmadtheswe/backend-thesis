package com.ahmadthesis.image.application.port.output;

import com.ahmadthesis.image.domain.image.PreOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PreOrderDatabase {
  Mono<PreOrder> save (PreOrder preOrder);
  Mono<PreOrder> getPreorderById (String preorderId);
  Mono<PreOrder> getPreorderByPreorderId (String preorderId);
  Mono<PreOrder> getPreorderByIdAndRequesterId(String id, String requesterId);
  Flux<PreOrder> getPreOrders();
  Flux<PreOrder> getPreOrderByRequesterId(String userId);
}
