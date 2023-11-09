package com.ahmadthesis.image.adapter.output.persistence.postgresql.data;

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
  private String mediaType;
  private Long createdAt;
  private Long latestAccess;
  private Boolean isPublic;
  @Column(value = "price_idr")
  private Long priceIDR;
  private BigDecimal latitude;
  private BigDecimal longitude;

  @Override
  public boolean isNew() {
    return true;
  }
}
