package com.ahmadthesis.payment.provider.payment;

import com.ahmadthesis.payment.business.PackageType;
import com.ahmadthesis.payment.business.Payment;
import com.ahmadthesis.payment.business.PaymentStatus;
import java.time.ZoneId;

public class PaymentConverter {

  public static PaymentEntity toEntity(Payment payment) {
    return PaymentEntity.builder()
        .id(payment.getId())
        .userId(payment.getUserId())
        .email(payment.getEmail())
        .packageId(payment.getPackageType().getName())
        .paymentStatus(payment.getPaymentStatus().getStatus())
        .payDate(payment.getPayDate() == null ? null : payment.getPayDate().toInstant())
        .validDate(payment.getValidDate() == null ? null : payment.getValidDate().toInstant())
        .build();
  }

  public static Payment toBusiness(PaymentEntity paymentEntity) {
    return Payment.builder()
        .id(paymentEntity.getId())
        .userId(paymentEntity.getUserId())
        .email(paymentEntity.getEmail())
        .packageType(PackageType.valueOf(paymentEntity.getPackageId()))
        .paymentStatus(PaymentStatus.valueOf(paymentEntity.getPaymentStatus()))
        .payDate(paymentEntity.getPayDate() == null ? null
            : paymentEntity.getPayDate().atZone(ZoneId.of("Asia/Jakarta")))
        .validDate(paymentEntity.getValidDate() == null ? null
            : paymentEntity.getValidDate().atZone(ZoneId.of("Asia/Jakarta")))
        .build();
  }
}
