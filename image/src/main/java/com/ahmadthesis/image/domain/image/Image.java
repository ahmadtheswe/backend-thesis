package com.ahmadthesis.image.domain.image;

import java.math.BigDecimal;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Generated
@Builder
@Data
public class Image {
  private String id;
  private String uploaderId;
  private String title;
  private String mediaType;
  private String filename;
  private String originalImageDir;
  private String thumbnailImageDir;
  private Long createdAt;
  private ProductLevel productLevel;
  private Boolean isPublic;
  private BigDecimal latitude;
  private BigDecimal longitude;

}
