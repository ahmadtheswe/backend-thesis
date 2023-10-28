package com.ahmadthesis.image.application.port.output;

import com.ahmadthesis.image.domain.image.ImageOwnership;
import reactor.core.publisher.Flux;

public interface OwnershipDatabase {
  Flux<ImageOwnership> getImageOwnershipByOwnerId(final String ownerId);
}
