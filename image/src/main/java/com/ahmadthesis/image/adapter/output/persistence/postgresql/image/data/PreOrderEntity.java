package com.ahmadthesis.image.adapter.output.persistence.postgresql.image.data;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Generated
@Table("public.\"preorder\"")
public class PreOrderEntity implements Persistable<String> {
  @Id
  private String id;
  private String requesterId;
  private BigDecimal centerLatitude;
  private BigDecimal centerLongitude;
  private BigDecimal topLeftLatitude;
  private BigDecimal topLeftLongitude;
  private BigDecimal topRightLatitude;
  private BigDecimal topRightLongitude;
  private BigDecimal bottomRightLatitude;
  private BigDecimal bottomRightLongitude;
  private BigDecimal bottomLeftLatitude;
  private BigDecimal bottomLeftLongitude;
  private Boolean isActive;
  private Long createdAt;
  private Long deliveredAt;

  @Setter
  @Transient
  private boolean isNew = true;

  public boolean isNew() { return isNew; }
}
