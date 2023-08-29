package com.ahmadthesis.image.adapter.output.persistence.postgresql.converter;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.data.ImageHistoryPostgre;
import com.ahmadthesis.image.domain.entity.image.ImageHistory;
import com.ahmadthesis.image.domain.objectvalue.image.Activity;
import org.springframework.stereotype.Component;

@Component
public class ImageHistoryConverter {
  public ImageHistory convertAdapterToDomain(ImageHistoryPostgre postgre) {
    ImageHistory imageHistory = new ImageHistory();
    imageHistory.setId(postgre.getId());
    imageHistory.setImageId(postgre.getImageId());
    imageHistory.setAccessorId(postgre.getAccessorId());
    imageHistory.setActivity(Activity.valueOf(postgre.getActivity()));
    imageHistory.setCreatedAt(postgre.getCreatedAt());

    return imageHistory;
  }

  public ImageHistoryPostgre convertDomainToAdapter(ImageHistory domain) {
    ImageHistoryPostgre postgre = new ImageHistoryPostgre();
    postgre.setId(domain.getId());
    postgre.setImageId(domain.getImageId());
    postgre.setAccessorId(domain.getAccessorId());
    postgre.setActivity(domain.getActivity().name());
    postgre.setCreatedAt(domain.getCreatedAt());

    return postgre;
  }
}
