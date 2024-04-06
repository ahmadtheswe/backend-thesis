package com.ahmadthesis.payment.controller.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PackageCountDTO {
  private String packageName;
  private Integer subscriptionTotal;
}
