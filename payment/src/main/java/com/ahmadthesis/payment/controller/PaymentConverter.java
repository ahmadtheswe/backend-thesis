package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.business.PackageType;
import com.ahmadthesis.payment.business.Payment;
import com.ahmadthesis.payment.controller.dto.PaymentDTO;
import com.ahmadthesis.payment.controller.dto.TransactionsDTO;

public class PaymentConverter {
  public static Payment toPayment(TransactionsDTO transactionsDTO) {
    return Payment.builder()
        .email(transactionsDTO.getEmail())
        .paymentType(transactionsDTO.getPaymentType())
        .packageType(PackageType.valueOf(transactionsDTO.getPackageType()))
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
