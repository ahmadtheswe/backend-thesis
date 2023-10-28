package com.ahmadthesis.image.domain.image;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ImageOwnership {
  private String id;
  private String imageId;
  private String ownerId;
}
