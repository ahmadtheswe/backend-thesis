package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.business.PackageType;
import com.ahmadthesis.payment.business.Payment;
import com.ahmadthesis.payment.controller.dto.PaymentDTO;
import com.ahmadthesis.payment.controller.dto.PaymentTransactionsDTO;

public class PaymentConverter {
  public static Payment toPayment(PaymentTransactionsDTO paymentTransactionsDTO) {
    return Payment.builder()
        .email(paymentTransactionsDTO.getEmail())
        .paymentType(paymentTransactionsDTO.getPaymentType())
        .packageType(PackageType.valueOf(paymentTransactionsDTO.getPackageType()))
        .build();
  }

  public static PaymentDTO toPaymentDTO(Payment payment) {
    return PaymentDTO.builder()
        .id(payment.getId())
        .userId(payment.getUserId())
        .email(payment.getEmail())
        .packageId(payment.getPackageType().getName())
        .paymentStatus(payment.getPaymentStatus().getStatus())
        .redirectUrl(payment.getRedirectUrl())
        .midtransToken(payment.getMidtransToken())
        .paymentDueDate(payment.getPaymentDueDate())
        .build();
  }
}
