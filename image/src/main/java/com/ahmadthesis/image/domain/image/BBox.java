package com.ahmadthesis.image.domain.image;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BBox {
  private BigDecimal minLongitude;
  private BigDecimal minLatitude;
  private BigDecimal maxLongitude;
  private BigDecimal maxLatitude;
}
