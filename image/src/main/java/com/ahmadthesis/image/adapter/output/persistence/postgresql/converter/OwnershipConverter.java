package com.ahmadthesis.image.adapter.output.persistence.postgresql.converter;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.OwnershipEntity;
import com.ahmadthesis.image.domain.image.ImageOwnership;

public final class OwnershipConverter {
  public static ImageOwnership convertEntityToDomain(OwnershipEntity entity) {
    return ImageOwnership.builder()
            .id(entity.getId())
            .imageId(entity.getImageId())
            .ownerId(entity.getOwnerId())
            .build();
  }

  public static OwnershipEntity convertDomainToEntity(ImageOwnership domain) {
    return OwnershipEntity.builder()
            .id(domain.getId())
            .imageId(domain.getImageId())
            .ownerId(domain.getOwnerId())
            .build();
  }
}
