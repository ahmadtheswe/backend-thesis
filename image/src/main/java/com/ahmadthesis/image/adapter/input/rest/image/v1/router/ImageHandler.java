package com.ahmadthesis.image.adapter.input.rest.image.v1.router;

import com.ahmadthesis.image.adapter.input.rest.common.dto.DataResponse;
import com.ahmadthesis.image.adapter.input.rest.image.v1.converter.ImageRestConverter;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.PaginationResponse;
import com.ahmadthesis.image.adapter.input.rest.image.v1.mapper.ImageMapper;
import com.ahmadthesis.image.application.usecase.ImageHistoryService;
import com.ahmadthesis.image.application.usecase.ImageService;
import com.ahmadthesis.image.application.usecase.ImageUploadService;
import com.ahmadthesis.image.domain.entity.image.Image;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component("ImageHandler")
public class ImageHandler {
  private final ImageUploadService imageUploadService;
  private final ImageService imageService;
  private final ImageHistoryService imageHistoryService;
  private final ImageRestConverter converter;
  private final ImageMapper mapper;

  public ImageHandler(
          ImageUploadService imageUploadService,
          ImageService imageService,
          ImageHistoryService imageHistoryService,
          ImageRestConverter converter,
          ImageMapper mapper
  ) {
    this.imageUploadService = imageUploadService;
    this.imageService = imageService;
    this.imageHistoryService = imageHistoryService;
    this.converter = converter;
    this.mapper = mapper;
  }

  Mono<ServerResponse> uploadImage(ServerRequest request) {
    return converter.extractUploadRequest(request).flatMap(saveImageRequest ->
            imageUploadService.upload(saveImageRequest.getImage())
                    .then(Mono.defer(() -> {
                      Image image = mapper.mapRequestToImage(saveImageRequest);
                      return imageService.save(image)
                              .flatMap(response -> ServerResponse.ok().bodyValue(
                                      new DataResponse<>(
                                              converter.generateUploadResponse(image),
                                              new ArrayList<>()
                                      )
                              ));
                    })));
  }

  Mono<ServerResponse> getImageById(ServerRequest request) {
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

  Mono<ServerResponse> getImagesPagination(ServerRequest request) {
    return converter.generatePaginationRequest(request)
            .flatMap(paginationRequest -> {
              Flux<Image> imageFlux = imageService.getImagesPagination(
                      paginationRequest.getSize(),
                      paginationRequest.getPage(),
                      paginationRequest.getSortBy());

              Mono<Long> totalImagesMono = imageService.getImagesCount();

              return imageFlux.collectList()
                      .zipWith(totalImagesMono)
                      .flatMap(tuple -> ServerResponse.ok()
                              .bodyValue(
                                      new PaginationResponse<>(
                                              tuple.getT1(),
                                              converter.generatePaginationInfo(paginationRequest, tuple.getT2()),
                                              new ArrayList<>()
                                      )
                              ));
            });
  }

}
