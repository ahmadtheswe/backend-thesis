package com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request;

import java.math.BigDecimal;
import lombok.*;

@AllArgsConstructor
@Builder
@Data
@Generated
@NoArgsConstructor
public class PaginationRequest {
  private Integer page;
  private Integer size;
  private String sortBy;
  private String title;
  private BigDecimal latitude;
  private BigDecimal longitude;
  private Double radius;
}
