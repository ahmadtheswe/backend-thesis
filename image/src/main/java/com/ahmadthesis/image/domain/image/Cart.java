package com.ahmadthesis.image.domain.image;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Cart {
  private String id;
  private String imageId;
  private String userId;
  private Long createdAt;
  private Boolean isActive;
}
