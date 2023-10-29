package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.application.port.output.OwnershipDatabase;
import com.ahmadthesis.image.application.usecase.OwnershipService;
import com.ahmadthesis.image.domain.image.ImageOwnership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OwnershipServiceImpl implements OwnershipService {
  private final OwnershipDatabase database;
  @Override
  public Flux<ImageOwnership> getImageOwnershipByOwnerId(String ownerId) {
    return database.getImageOwnershipByOwnerId(ownerId);
  }

  @Override
  public Mono<ImageOwnership> getImageByOwnershipByOwnerIdAndUserId(String ownerId, String userId) {
    return database.getImageByOwnershipByOwnerIdAndImageId(ownerId, userId);
  }
}
