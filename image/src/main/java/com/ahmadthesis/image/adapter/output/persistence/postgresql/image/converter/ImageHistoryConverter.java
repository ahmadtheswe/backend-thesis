package com.ahmadthesis.image.adapter.output.persistence.postgresql.image.converter;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.data.ImageHistoryEntity;
import com.ahmadthesis.image.domain.image.ImageHistory;
import com.ahmadthesis.image.domain.image.Activity;

public final class ImageHistoryConverter {
  public static ImageHistory convertEntityToDomain(final ImageHistoryEntity entity) {
    final ImageHistory imageHistory = new ImageHistory();
    imageHistory.setId(entity.getId());
    imageHistory.setImageId(entity.getImageId());
    imageHistory.setAccessorId(entity.getAccessorId());
    imageHistory.setActivity(Activity.valueOf(entity.getActivity()));
    imageHistory.setCreatedAt(entity.getCreatedAt());

    return imageHistory;
  }

  public static ImageHistoryEntity convertDomainToEntity(final ImageHistory domain) {
    final ImageHistoryEntity postgre = new ImageHistoryEntity();
    postgre.setId(domain.getId());
    postgre.setImageId(domain.getImageId());
    postgre.setAccessorId(domain.getAccessorId());
    postgre.setActivity(domain.getActivity().name());
    postgre.setCreatedAt(domain.getCreatedAt());

    return postgre;
  }
}
