package com.ahmadthesis.payment.provider.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentPackageSummaryEntity {
  private String packageId;
  private Integer userCount;
}
