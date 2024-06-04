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
  private String requesterEmail;
  private String requesterUsername;
  private BBox bBox;
  private Coordinate centerCoordinate;
  private Boolean isActive;
  private Long createdAt;
  private Long deliveredAt;
  private String paymentPreorderId;
  private Boolean isNew;
}
