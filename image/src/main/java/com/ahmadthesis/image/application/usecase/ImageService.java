package com.ahmadthesis.image.application.usecase;

import com.ahmadthesis.image.domain.entity.image.Image;
import reactor.core.publisher.Mono;

public interface ImageService {
    Mono<Image> save(Image image);
    Mono<Image> getImageById(String id);
}
