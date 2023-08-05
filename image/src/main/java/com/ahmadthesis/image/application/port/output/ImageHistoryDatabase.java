package com.ahmadthesis.image.application.port.output;

import com.ahmadthesis.image.domain.entity.image.ImageHistory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageHistoryDatabase {
    Mono<ImageHistory> save(ImageHistory imageHistory);
    Flux<ImageHistory> getHistoryByImageId(String imageId);
}
