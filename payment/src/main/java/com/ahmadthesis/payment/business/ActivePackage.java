package com.ahmadthesis.payment.business;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ActivePackage {
  private PackageType activePackage;
  private ZonedDateTime activeUntil;
}
