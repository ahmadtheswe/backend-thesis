package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.business.PackageType;
import com.ahmadthesis.payment.business.Payment;

public class TransactionConverter {
  public static Payment toPayment(TransactionsDTO transactionsDTO) {
    return Payment.builder()
        .userId(transactionsDTO.getUserId())
        .email(transactionsDTO.getEmail())
        .paymentType(transactionsDTO.getPaymentType())
        .packageType(PackageType.valueOf(transactionsDTO.getPackageType()))
        .build();
  }
}
