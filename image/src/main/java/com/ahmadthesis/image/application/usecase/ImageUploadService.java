package com.ahmadthesis.image.application.usecase;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface ImageUploadService {
    Mono<String> storeImage(MultipartFile file);
}
