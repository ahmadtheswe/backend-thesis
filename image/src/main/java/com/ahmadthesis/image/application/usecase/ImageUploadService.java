package com.ahmadthesis.image.application.usecase;

import org.springframework.data.util.Pair;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface ImageUploadService {
  Mono<Pair<Boolean, String>> storeImage(MultipartFile file);
  Mono<Void> upload(FilePart file, FilePart thumbnail, String filename);
}
