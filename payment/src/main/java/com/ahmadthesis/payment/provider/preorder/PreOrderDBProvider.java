package com.ahmadthesis.payment.provider.preorder;

import com.ahmadthesis.payment.business.PreOrder;
import com.ahmadthesis.payment.usecase.PreOrderPersister;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PreOrderDBProvider implements PreOrderPersister {

  @Value("${midtrans.server-key}")
  private String serverKey;

  @Value("${time.zone}")
  private String zoneDateTime;

  private final PreOrderRepository preOrderRepository;

  @Override
  public Mono<Void> savePreOrder(PreOrder preOrder, Boolean isNew) {
    PreOrderEntity entity = PreOrderConverter.toEntity(preOrder, isNew);
    return preOrderRepository.save(entity).then();
  }
}
