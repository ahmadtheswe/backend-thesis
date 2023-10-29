package com.ahmadthesis.image.adapter.output.persistence.postgresql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.converter.OwnershipConverter;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.sql.R2DBCOwnershipRepository;
import com.ahmadthesis.image.application.port.output.OwnershipDatabase;
import com.ahmadthesis.image.domain.image.ImageOwnership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OwnershipPostgreAdapter implements OwnershipDatabase {
  private final R2DBCOwnershipRepository repository;

  @Override
  public Flux<ImageOwnership> getImageOwnershipByOwnerId(String ownerId) {
    return repository.findAllByOwnerId(ownerId)
            .map(OwnershipConverter::convertEntityToDomain);
  }

  @Override
  public Mono<ImageOwnership> getImageByOwnershipByOwnerIdAndImageId(String ownerId, String imageId) {
    return repository.findByOwnerIdAndImageId(ownerId, imageId)
            .map(OwnershipConverter::convertEntityToDomain);
  }
}
