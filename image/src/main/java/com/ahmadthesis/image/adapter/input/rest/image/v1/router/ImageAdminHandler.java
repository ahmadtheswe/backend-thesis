package com.ahmadthesis.image.adapter.input.rest.image.v1.router;

import com.ahmadthesis.image.adapter.input.rest.common.dto.DataResponse;
import com.ahmadthesis.image.adapter.input.rest.image.v1.converter.ImageRestConverter;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.PaginationResponse;
import com.ahmadthesis.image.adapter.input.rest.image.v1.mapper.ImageMapper;
import com.ahmadthesis.image.application.usecase.ImageService;
import com.ahmadthesis.image.application.usecase.ImageUploadService;
import com.ahmadthesis.image.domain.image.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component("ImageAdminHandler")
@RequiredArgsConstructor
public class ImageAdminHandler {
  private final ImageUploadService imageUploadService;
  private final ImageService imageService;

  Mono<ServerResponse> uploadImage(final ServerRequest request) {
    return ImageRestConverter.extractUploadRequest(request).flatMap(saveImageRequest ->
            imageUploadService.upload(saveImageRequest.getImage(), saveImageRequest.getFilename())
                    .then(Mono.defer(() -> {
                      final Image image = ImageMapper.mapRequestToImage(saveImageRequest);
                      return imageService.save(image)
                              .flatMap(response -> ServerResponse.ok().bodyValue(
                                      new DataResponse<>(
                                              ImageRestConverter.generateUploadResponse(image),
                                              new ArrayList<>()
                                      )
                              ));
                    }))
                    .onErrorResume(throwable -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .bodyValue("An error occurred during image upload or processing.")));
  }

  Mono<ServerResponse> getImageById(final ServerRequest request) {
    final String imageId = request.queryParam("id").orElse(null);
    if (imageId == null) {
      return ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(
              new DataResponse<>(null, List.of("Image ID should not be null"))
      );
    }
    return imageService.getImageById(imageId)
            .flatMap(image -> ServerResponse.ok().bodyValue(
                    new DataResponse<>(
                            image,
                            new ArrayList<>()
                    )
            ));
  }

  Mono<ServerResponse> getImagesPagination(final ServerRequest request) {
    return ImageRestConverter.generatePaginationRequest(request)
            .flatMap(paginationRequest -> {
              final Flux<Image> imageFlux = imageService.getImagesPagination(
                      paginationRequest.getSize(),
                      paginationRequest.getPage(),
                      paginationRequest.getSortBy());

              final Mono<Long> totalImagesMono = imageService.getImagesCount();

              return imageFlux.collectList()
                      .zipWith(totalImagesMono)
                      .flatMap(tuple -> ServerResponse.ok()
                              .bodyValue(
                                      new PaginationResponse<>(
                                              tuple.getT1(),
                                              ImageRestConverter
                                                      .generatePaginationInfo(paginationRequest, tuple.getT2()),
                                              new ArrayList<>()
                                      )
                              ));
            });
  }

  Mono<ServerResponse> viewImageFile(final ServerRequest request) {
    return ImageRestConverter.extractIdRequest(request)
            .flatMap(imageService::getImageById)
            .flatMap(image -> {
              final File file = new File(image.getOriginalImageDir());
              final Resource resource = new FileSystemResource(file);
              return ServerResponse.ok()
                      .bodyValue(resource);
            })
            .switchIfEmpty(Mono.defer(() -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(
                    new DataResponse<>(
                            null,
                            new ArrayList<>() {{
                              add(HttpStatus.NOT_FOUND.toString());
                            }}
                    )
            )));
  }

  public Mono<ServerResponse> handleRequest(ServerRequest request) {
    return request.principal()
            .flatMap(principal -> {
              String username = principal.getName();
              return ServerResponse.ok().bodyValue(username);
            });
  }

}
