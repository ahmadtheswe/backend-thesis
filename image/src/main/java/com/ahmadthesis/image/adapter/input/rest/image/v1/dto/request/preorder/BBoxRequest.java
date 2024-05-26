package com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.preorder;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BBoxRequest {
  private BigDecimal minLongitude;
  private BigDecimal minLatitude;
  private BigDecimal maxLongitude;
  private BigDecimal maxLatitude;
}
