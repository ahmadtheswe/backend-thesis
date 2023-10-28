package com.ahmadthesis.image.adapter.input.rest.image.v1.router;

import com.ahmadthesis.image.adapter.input.rest.image.v1.converter.ImageRestConverter;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.PaginationResponse;
import com.ahmadthesis.image.application.usecase.ImageService;
import com.ahmadthesis.image.domain.image.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Component("ImageRegularHandler")
@RequiredArgsConstructor
public class ImageRegularHandler {
  private final ImageService imageService;

  Mono<ServerResponse> getImagesPagination(final ServerRequest request) {
    return ImageRestConverter.generatePaginationRequest(request)
            .flatMap(paginationRequest -> {
              final Flux<Image> imageFlux = imageService.getPublicImagesPagination(
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

  Mono<ServerResponse> getUserImagesCollectionPagination(final ServerRequest request) {
    return request.principal().flatMap(principal -> Mono.just(principal.getName()))
            .flatMap(userId -> ImageRestConverter.generatePaginationRequest(request)
                    .flatMap(paginationRequest -> {
                      final Flux<Image> imageFlux = imageService.getUserImagesCollectionPagination(
                              paginationRequest.getSize(),
                              paginationRequest.getPage(),
                              paginationRequest.getSortBy(),
                              userId);

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
                    }));
  }
}
