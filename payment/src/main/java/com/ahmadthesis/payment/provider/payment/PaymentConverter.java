package com.ahmadthesis.payment.provider.payment;

import com.ahmadthesis.payment.business.Payment;
import com.ahmadthesis.payment.business.PaymentStatus;
import java.time.ZoneId;

public class PaymentConverter {
  public static PaymentEntity toEntity(Payment payment) {
    return PaymentEntity.builder()
        .id(payment.getId())
        .userId(payment.getUserId())
        .packageId(payment.getPackageId())
        .payDate(payment.getPayDate().toInstant())
        .paymentStatus(payment.getPaymentStatus().getStatus())
        .validDate(payment.getValidDate().toInstant())
        .build();
  }

  public static Payment toBusiness(PaymentEntity paymentEntity) {
    return Payment.builder()
        .id(paymentEntity.getId())
        .userId(paymentEntity.getUserId())
        .packageId(paymentEntity.getPackageId())
        .paymentStatus(PaymentStatus.valueOf(paymentEntity.getPaymentStatus()))
        .payDate(paymentEntity.getPayDate().atZone(ZoneId.of("Asia/Jakarta")))
        .validDate(paymentEntity.getValidDate().atZone(ZoneId.of("Asia/Jakarta")))
        .build();
  }
}
