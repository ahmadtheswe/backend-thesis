package com.ahmadthesis.payment.controller.dto;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentDTO {
  private String id;
  private String userId;
  private String email;
  private String packageId;
  private String paymentStatus;
  private String redirectUrl;
  private String midtransToken;
  private ZonedDateTime paymentDueDate;
}
