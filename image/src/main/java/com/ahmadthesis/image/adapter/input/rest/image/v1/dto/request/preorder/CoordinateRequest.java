package com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.preorder;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CoordinateRequest {
  private BigDecimal latitude;
  private BigDecimal longitude;
}
