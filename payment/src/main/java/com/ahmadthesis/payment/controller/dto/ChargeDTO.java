package com.ahmadthesis.payment.controller.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChargeDTO {
  private String redirectUrl;
  private String orderId;
  private String midtransToken;
}
