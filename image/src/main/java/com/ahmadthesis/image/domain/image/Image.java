package com.ahmadthesis.image.domain.image;

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
  private Long createdAt;
  private Long latestAccess;
  private Boolean isPublic;

}
