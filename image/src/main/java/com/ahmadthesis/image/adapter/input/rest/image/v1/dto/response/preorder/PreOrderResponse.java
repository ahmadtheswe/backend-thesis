package com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.preorder;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PreOrderResponse {
  private String id;
  private CoordinateResponse centerCoordinateResponse;
  private BBoxResponse bBoxResponse;
  private Boolean isActive;
  private String createdAt;
  private String deliveredAt;
  private Boolean isPaid;
  private String redirectUrl;
  private String probeType;
}
