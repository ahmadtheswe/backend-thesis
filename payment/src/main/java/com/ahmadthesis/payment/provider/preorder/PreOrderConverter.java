package com.ahmadthesis.payment.provider.preorder;

import com.ahmadthesis.payment.business.PreOrder;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class PreOrderConverter {

  public static PreOrderEntity toEntity(PreOrder preOrder, Boolean isNew) {
    return PreOrderEntity.builder()
        .id(preOrder.getId())
        .userId(preOrder.getUserId())
        .userEmail(preOrder.getUserEmail())
        .price(preOrder.getPrice())
        .isPaid(preOrder.getIsPaid())
        .createdAt(ZonedDateTime.now(ZoneId.of("Asia/Jakarta")).toInstant())
        .paidAt(preOrder.getPaidAt() != null ? preOrder.getPaidAt().toInstant() : null)
        .redirectUrl(preOrder.getRedirectUrl())
        .midtransToken(preOrder.getMidtransToken())
        .paymentDueDate(ZonedDateTime.now(ZoneId.of("Asia/Jakarta")).plusDays(1).toInstant())
        .isNew(isNew)
        .build();
  }
}
