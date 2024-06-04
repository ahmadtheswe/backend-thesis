package com.ahmadthesis.payment.business;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PreOrder {

  private String id;
  private String userId;
  private String userEmail;
  private BigInteger price;
  private Double imageSize;
  private Boolean isPaid;
  private ZonedDateTime createdAt;
  private ZonedDateTime paidAt;
  private String redirectUrl;
  private String midtransToken;
  private ZonedDateTime paymentDueDate;
}
