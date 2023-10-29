package com.ahmadthesis.image.adapter.output.persistence.postgresql.converter;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.CartEntity;
import com.ahmadthesis.image.domain.image.Cart;

public final class CartConverter {
  public static Cart convertFromEntityToDomain(final CartEntity entity) {
    return Cart.builder()
            .id(entity.getId())
            .imageId(entity.getImageId())
            .userId(entity.getUserId())
            .createdAt(entity.getCreatedAt())
            .isActive(entity.getIsActive())
            .build();
  }

  public static CartEntity convertFromDomainToEntity(final Cart domain) {
    return CartEntity.builder()
            .id(domain.getId())
            .imageId(domain.getImageId())
            .userId(domain.getUserId())
            .createdAt(domain.getCreatedAt())
            .isActive(domain.getIsActive())
            .build();
  }

}
