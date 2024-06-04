package com.ahmadthesis.payment.provider.preorder;

import com.ahmadthesis.payment.business.PaymentCallBack;
import com.ahmadthesis.payment.business.PreOrder;
import com.ahmadthesis.payment.usecase.PreOrderPersister;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PreOrderDBProvider implements PreOrderPersister {

  @Value("${midtrans.server-key}")
  private String MIDTRANS_SERVER_KEY;

  @Value("${time.zone}")
  private String zoneDateTime;

  private final PreOrderRepository preOrderRepository;

  @Override
  public Mono<Void> savePreOrder(PreOrder preOrder, Boolean isNew) {
    PreOrderEntity entity = PreOrderConverter.toEntity(preOrder, isNew);
    return preOrderRepository.save(entity).then();
  }
}
