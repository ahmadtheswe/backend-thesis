package com.ahmadthesis.payment.business;

import java.time.Instant;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Payment {
  private String id;
  private String userId;
  private String packageId;
  private PaymentStatus paymentStatus;
  private ZonedDateTime payDate;
  private ZonedDateTime validDate;
}
