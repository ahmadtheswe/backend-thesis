package com.ahmadthesis.image.adapter.output.persistence.postgresql.image;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.converter.ImageHistoryConverter;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.image.sql.ImageHistoryRepository;
import com.ahmadthesis.image.application.port.output.ImageHistoryDatabase;
import com.ahmadthesis.image.domain.image.ImageHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ImageHistoryPostgreAdapter implements ImageHistoryDatabase {

  private final ImageHistoryRepository repository;

  @Override
  public Mono<ImageHistory> save(ImageHistory imageHistory) {
    return repository.save(ImageHistoryConverter.convertDomainToEntity(imageHistory))
        .map(ImageHistoryConverter::convertEntityToDomain);
  }

  @Override
  public Flux<ImageHistory> getHistoryByImageId(String imageId) {
    return repository.getImageHistoryPostgreByImageId(imageId)
        .map(ImageHistoryConverter::convertEntityToDomain);
  }
}
