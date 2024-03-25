package com.ahmadthesis.payment.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MidtransCallBackDTO {
  @JsonProperty("order_id")
  private String orderId;

  @JsonProperty("status_code")
  private String statusCode;

  @JsonProperty("gross_amount")
  private String grossAmount;

  @JsonProperty("signature_key")
  private String signatureKey;

  @JsonProperty("transaction_status")
  private String transactionStatus;

  @JsonProperty("transaction_time")
  private String transactionTime;

}
