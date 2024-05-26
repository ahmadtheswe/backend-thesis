package com.ahmadthesis.image.domain.image;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Coordinate {
  private BigDecimal latitude;
  private BigDecimal longitude;
}
