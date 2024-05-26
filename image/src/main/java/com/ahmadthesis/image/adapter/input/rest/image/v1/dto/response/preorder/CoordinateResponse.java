package com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.preorder;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CoordinateResponse {
  private BigDecimal latitude;
  private BigDecimal longitude;
}
