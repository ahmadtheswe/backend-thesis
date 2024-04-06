package com.ahmadthesis.payment.controller.dto;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ActivePackageDTO {
  private String activePackage;
  private ZonedDateTime activeUntil;
}
