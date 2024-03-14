package com.ahmadthesis.payment.provider.payment;

import com.ahmadthesis.payment.business.PackageType;
import com.ahmadthesis.payment.business.Payment;
import com.ahmadthesis.payment.business.PaymentStatus;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
        .redirectUrl(payment.getRedirectUrl())
        .midtransToken(payment.getMidtransToken())
        .paymentDueDate(ZonedDateTime.now(ZoneId.of("Asia/Jakarta")).plusDays(1).toInstant())
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
        .paymentDueDate(paymentEntity.getPaymentDueDate() == null ? null
            : paymentEntity.getPaymentDueDate().atZone(ZoneId.of("Asia/Jakarta")))
        .build();
  }
}
