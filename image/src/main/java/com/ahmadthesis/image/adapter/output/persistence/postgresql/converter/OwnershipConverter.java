package com.ahmadthesis.image.adapter.output.persistence.postgresql.converter;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.OwnershipPostgre;
import com.ahmadthesis.image.domain.entity.image.ImageOwnership;

public final class OwnershipConverter {

  public static ImageOwnership convertEntityToDomain(OwnershipPostgre entity) {
    return ImageOwnership.builder()
            .id(entity.getId())
            .imageId(entity.getImageId())
            .ownerId(entity.getOwnerId())
            .build();
  }

  public static OwnershipPostgre convertDomainToEntity(ImageOwnership domain) {
    return OwnershipPostgre.builder()
            .id(domain.getId())
            .imageId(domain.getImageId())
            .ownerId(domain.getOwnerId())
            .build();
  }
}
