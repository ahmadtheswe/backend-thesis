package com.ahmadthesis.payment.business;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class Charge {
  private String redirectUrl;
  private String orderId;
  private String midtransToken;
}
