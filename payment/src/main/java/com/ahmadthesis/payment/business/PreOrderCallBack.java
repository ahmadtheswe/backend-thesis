package com.ahmadthesis.payment.business;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PreOrderCallBack {
  private String orderId;
  private String statusCode;
  private String grossAmount;
  private String signatureKey;
  private String transactionStatus;
  private String transactionTime;
}
