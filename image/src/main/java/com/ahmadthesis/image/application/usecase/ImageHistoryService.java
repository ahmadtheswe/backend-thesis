package com.ahmadthesis.image.application.usecase;

import com.ahmadthesis.image.domain.entity.image.ImageHistory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageHistoryService {
  Mono<ImageHistory> save(ImageHistory imageHistory);

  Flux<ImageHistory> getHistoryByImageId(String imageId);
}
