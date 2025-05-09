package com.ahmadthesis.image.adapter.output.persistence.postgresql.image.data;

import java.math.BigDecimal;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Generated
@Table("public.\"image\"")
public class ImageEntity implements Persistable<String> {
  @Id
  private String id;
  private String uploaderId;
  private String title;
  private String filename;
  private String originalImageDir;
  private String thumbnailImageDir;
  private String mediaType;
  private Long createdAt;
  private String productLevel;
  private Boolean isPublic;
  private BigDecimal latitude;
  private BigDecimal longitude;

  @Override
  public boolean isNew() {
    return true;
  }
}
