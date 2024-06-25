package com.ahmadthesis.payment.business;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CopernicusPrice {
  private String id;
  private String name;
  private String price;
  private ZonedDateTime updatedAt;
}
