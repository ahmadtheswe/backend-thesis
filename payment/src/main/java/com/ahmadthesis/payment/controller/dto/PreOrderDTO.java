package com.ahmadthesis.payment.controller.dto;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PreOrderDTO {
  private String id;
  private String userId;
  private String userEmail;
  private Boolean isPaid;
  private String redirectUrl;
  private String midtransToken;
  private ZonedDateTime paymentDueDate;
}
