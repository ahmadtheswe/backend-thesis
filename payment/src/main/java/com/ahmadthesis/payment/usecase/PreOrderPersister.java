package com.ahmadthesis.payment.usecase;

import com.ahmadthesis.payment.business.PreOrder;
import reactor.core.publisher.Mono;

public interface PreOrderPersister {
  Mono<Void> savePreOrder(PreOrder preOrder, Boolean isNew);
}
