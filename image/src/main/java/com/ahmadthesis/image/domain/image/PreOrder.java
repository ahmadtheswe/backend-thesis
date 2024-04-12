package com.ahmadthesis.image.domain.image;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Generated
@Builder
@Data
public class PreOrder {
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
}
