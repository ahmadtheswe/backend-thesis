package com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ImageResponse {

  private String title;
  private String mediaType;
  private String filename;
  private String productLevel;
  private BigDecimal latitude;
  private BigDecimal longitude;
}
