package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.business.Charge;
import com.ahmadthesis.payment.controller.dto.ChargeDTO;

public class ChargeConverter {
  public static ChargeDTO toDTO(Charge charge) {
    return ChargeDTO.builder()
        .orderId(charge.getOrderId())
        .redirectUrl(charge.getRedirectUrl())
        .midtransToken(charge.getMidtransToken())
        .build();
  }
}
