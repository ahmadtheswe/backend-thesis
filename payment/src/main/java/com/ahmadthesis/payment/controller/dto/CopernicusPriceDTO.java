package com.ahmadthesis.payment.controller.dto;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CopernicusPriceDTO {
  private String id;
  private String name;
  private String price;
  private ZonedDateTime updatedAt;
}
