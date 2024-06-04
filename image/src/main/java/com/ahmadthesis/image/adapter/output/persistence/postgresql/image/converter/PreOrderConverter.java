package com.ahmadthesis.image.adapter.output.persistence.postgresql.image.converter;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.data.PreOrderEntity;
import com.ahmadthesis.image.domain.image.BBox;
import com.ahmadthesis.image.domain.image.Coordinate;
import com.ahmadthesis.image.domain.image.PreOrder;
import org.springframework.stereotype.Component;

@Component
public class PreOrderConverter {

  public static PreOrder toDomain(PreOrderEntity entity) {
    return PreOrder.builder()
        .id(entity.getId())
        .requesterId(entity.getRequesterId())
        .requesterEmail(entity.getRequesterEmail())
        .requesterUsername(entity.getRequesterUsername())
        .bBox(BBox.builder()
            .maxLongitude(entity.getMaxLongitude())
            .maxLatitude(entity.getMaxLatitude())
            .minLongitude(entity.getMinLongitude())
            .minLatitude(entity.getMinLatitude())
            .build())
        .centerCoordinate(Coordinate.builder()
            .latitude(entity.getCenterLatitude())
            .longitude(entity.getCenterLongitude())
            .build())
        .isActive(entity.getIsActive())
        .createdAt(entity.getCreatedAt())
        .deliveredAt(entity.getDeliveredAt())
        .paymentPreorderId(entity.getPaymentPreorderId())
        .isNew(entity.isNew())
        .build();
  }

  public static PreOrderEntity toEntity(PreOrder domain) {
    return PreOrderEntity.builder()
        .id(domain.getId())
        .requesterId(domain.getRequesterId())
        .requesterEmail(domain.getRequesterEmail())
        .requesterUsername(domain.getRequesterUsername())
        .centerLatitude(domain.getCenterCoordinate().getLatitude())
        .centerLongitude(domain.getCenterCoordinate().getLongitude())
        .maxLongitude(domain.getBBox().getMaxLongitude())
        .maxLatitude(domain.getBBox().getMaxLatitude())
        .minLongitude(domain.getBBox().getMinLongitude())
        .minLatitude(domain.getBBox().getMinLatitude())
        .isActive(domain.getIsActive())
        .createdAt(domain.getCreatedAt())
        .deliveredAt(domain.getDeliveredAt())
        .paymentPreorderId(domain.getPaymentPreorderId())
        .isNew(domain.getIsNew())
        .build();
  }
}
