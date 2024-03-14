package com.ahmadthesis.payment.business;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Charge {
  private String redirectUrl;
  private String orderId;
  private String midtransToken;
}
