package com.ahmadthesis.payment.business;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CopernicusPrice {
  private String id;
  private String name;
  private BigInteger price;
  private ZonedDateTime updatedAt;
}
