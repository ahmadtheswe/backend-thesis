package com.ahmadthesis.payment.controller.dto;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CopernicusPriceDTO {
  private String id;
  private String name;
  private BigInteger price;
  private ZonedDateTime updatedAt;
}
