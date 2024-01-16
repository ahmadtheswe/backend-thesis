package com.ahmadthesis.payment.business;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Package {
  private String id;
  private String packageName;
  private Long price;
  private Boolean isActive;
}
