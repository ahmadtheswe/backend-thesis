package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.business.PaymentCallBack;
import com.ahmadthesis.payment.controller.dto.MidtransCallBackDTO;

public class PaymentCallBackConverter {
  public static PaymentCallBack toBusiness(MidtransCallBackDTO dto) {
    return PaymentCallBack.builder()
        .orderId(dto.getOrderId())
        .statusCode(dto.getStatusCode())
        .grossAmount(dto.getGrossAmount())
        .signatureKey(dto.getSignatureKey())
        .transactionStatus(dto.getTransactionStatus())
        .transactionTime(dto.getTransactionTime())
        .build();
  }
}
