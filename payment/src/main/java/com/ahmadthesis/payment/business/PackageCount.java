package com.ahmadthesis.payment.business;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PackageCount {
  private String packageName;
  private Integer subscriptionTotal;
}
