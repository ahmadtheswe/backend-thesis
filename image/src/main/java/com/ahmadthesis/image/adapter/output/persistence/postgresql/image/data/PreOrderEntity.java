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
  private String requesterEmail;
  private String requesterUsername;
  private BigDecimal centerLatitude;
  private BigDecimal centerLongitude;
  private BigDecimal maxLatitude;
  private BigDecimal maxLongitude;
  private BigDecimal minLatitude;
  private BigDecimal minLongitude;
  private Boolean isActive;
  private Long createdAt;
  private Long deliveredAt;
  private String paymentPreorderId;

  @Transient
  private boolean isNew = true;

  public void setIsNew(boolean isNew) { this.isNew = isNew; }
  public boolean isNew() { return isNew; }
}
