package com.ahmadthesis.image.adapter.output.persistence.postgresql.image.converter;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.data.PreOrderEntity;
import com.ahmadthesis.image.domain.image.PreOrder;
import org.springframework.stereotype.Component;

@Component
public class PreOrderConverter {
  public static PreOrder toDomain(PreOrderEntity entity) {
    return PreOrder.builder()
        .id(entity.getId())
        .requesterId(entity.getRequesterId())
        .centerLatitude(entity.getCenterLatitude())
        .centerLongitude(entity.getCenterLongitude())
        .topLeftLatitude(entity.getTopLeftLatitude())
        .topLeftLongitude(entity.getTopLeftLongitude())
        .topRightLatitude(entity.getTopRightLatitude())
        .topRightLongitude(entity.getTopRightLongitude())
        .bottomRightLatitude(entity.getBottomRightLatitude())
        .bottomRightLongitude(entity.getBottomRightLongitude())
        .bottomLeftLatitude(entity.getBottomLeftLatitude())
        .bottomLeftLongitude(entity.getBottomLeftLongitude())
        .isActive(entity.getIsActive())
        .createdAt(entity.getCreatedAt())
        .deliveredAt(entity.getDeliveredAt())
        .build();
  }

  public static PreOrderEntity toEntity(PreOrder domain) {
    return PreOrderEntity.builder()
        .id(domain.getId())
        .requesterId(domain.getRequesterId())
        .centerLatitude(domain.getCenterLatitude())
        .centerLongitude(domain.getCenterLongitude())
        .topLeftLatitude(domain.getTopLeftLatitude())
        .topLeftLongitude(domain.getTopLeftLongitude())
        .topRightLatitude(domain.getTopRightLatitude())
        .topRightLongitude(domain.getTopRightLongitude())
        .bottomRightLatitude(domain.getBottomRightLatitude())
        .bottomRightLongitude(domain.getBottomRightLongitude())
        .bottomLeftLatitude(domain.getBottomLeftLatitude())
        .bottomLeftLongitude(domain.getBottomLeftLongitude())
        .isActive(domain.getIsActive())
        .createdAt(domain.getCreatedAt())
        .deliveredAt(domain.getDeliveredAt())
        .build();
  }
}
