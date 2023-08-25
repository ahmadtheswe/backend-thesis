package com.ahmadthesis.image.adapter.input.rest.image.v1.converter;

import com.ahmadthesis.image.adapter.input.rest.common.parser.FilePartParser;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.ImageUploadResponse;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.SaveImageRequest;
import com.ahmadthesis.image.domain.entity.image.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

@Component
public class ImageRestConverter {

    @Value("${directory.image}")
    private String UPLOAD_DIR;

    public ImageUploadResponse uploadResponse(Image image) {
        return ImageUploadResponse.builder()
                .id(image.getId())
                .title(image.getTitle())
                .build();
    }

    public Mono<SaveImageRequest> extract(ServerRequest request) {
        return request.multipartData().flatMap(stringPartMultiValueMap -> {
            FilePart image = (FilePart) stringPartMultiValueMap.getFirst("image");
            String title = FilePartParser.parsePartToString(
                    Objects.requireNonNull(stringPartMultiValueMap.getFirst("title")));
            Boolean isPublic = Boolean.valueOf(FilePartParser
                    .parsePartToString(Objects.requireNonNull(stringPartMultiValueMap.getFirst("isPublic"))));
            String filename = Objects.requireNonNull(image).filename();
            String mediaType = getExtensionByStringHandling(filename);
            String uploadDir = Paths.get(UPLOAD_DIR + File.separator + filename).toString();

            return Mono.just(SaveImageRequest.builder()
                    .image(image)
                    .title(title)
                    .isPublic(isPublic)
                    .filename(filename)
                    .mediaType(mediaType)
                    .uploadDir(uploadDir)
                    .build());
        });
    }

    private String getExtensionByStringHandling(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public String extractMediaType(MultipartFile file) {
        return file.getContentType();
    }
}
