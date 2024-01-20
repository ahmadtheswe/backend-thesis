package com.ahmadthesis.payment.controller;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentStatusDTO {
  private String orderId;
  private String transactionStatus;
  private String amount;
}
