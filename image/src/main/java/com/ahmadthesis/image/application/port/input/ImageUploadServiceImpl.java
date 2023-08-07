package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.application.usecase.ImageUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    private static final String UPLOAD_DIRECTORY = "/uploads";

    @Override
    public Mono<String> storeImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIRECTORY, fileName);

        return Mono.fromCallable(() -> {
            file.transferTo(filePath);
            return filePath.toString();
        }).onErrorResume(Exception.class, ex -> Mono.empty());
    }
}
