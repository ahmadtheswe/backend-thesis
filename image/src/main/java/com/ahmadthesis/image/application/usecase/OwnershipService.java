package com.ahmadthesis.image.application.usecase;

import com.ahmadthesis.image.domain.image.ImageOwnership;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OwnershipService {
  Flux<ImageOwnership> getImageOwnershipByOwnerId(final String ownerId);
  Mono<ImageOwnership> getImageByOwnershipByOwnerIdAndUserId(final String ownerId, final String userId);
}
