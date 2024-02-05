package com.ahmadthesis.payment.business;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ActivePackage {
  private PackageType activePackage;
}
