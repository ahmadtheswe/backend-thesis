package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.application.usecase.ImageUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {

  @Value("${directory.image}")
  private String UPLOAD_DIRECTORY;

  @Override
  public Mono<Pair<Boolean, String>> storeImage(MultipartFile file) {
    String fileName = file.getOriginalFilename();
    Path filePath = Paths.get(UPLOAD_DIRECTORY, fileName);

    return Mono.fromCallable(() -> {
      file.transferTo(filePath);
      return Pair.of(true, filePath.toString());
    }).onErrorResume(Exception.class, ex -> Mono.just(Pair.of(false, "")));
  }

  @Override
  public Mono<Void> upload(FilePart filePart) {
    return filePart.transferTo(Paths.get(UPLOAD_DIRECTORY + File.separator + filePart.filename()));
  }
}
