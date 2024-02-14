package com.ahmadthesis.image.domain.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Generated
@Data
public class ImageHistory {
  private String id;
  private String imageId;
  private String accessorId;
  private Activity activity;
  private Long createdAt;
}
