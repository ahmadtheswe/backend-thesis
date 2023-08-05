package com.ahmadthesis.image.application.port.output;

import com.ahmadthesis.image.domain.entity.image.Image;
import reactor.core.publisher.Mono;

public interface ImageDatabase {
    Mono<Image> save(Image image);
    Mono<Image> getImageById(String id);
}
