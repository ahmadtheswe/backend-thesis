package com.ahmadthesis.payment.business;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PreOrder {

  private String id;
  private String userId;
  private String userEmail;
  private BigDecimal price;
  private Double imageSize;
  private Boolean isPaid;
  private ZonedDateTime createdAt;
  private ZonedDateTime paidAt;
  private String redirectUrl;
  private String midtransToken;
  private ZonedDateTime paymentDueDate;
}
