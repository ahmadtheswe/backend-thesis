package com.ahmadthesis.image.adapter.input.rest.image.v1.router;

import com.ahmadthesis.image.adapter.input.rest.common.dto.DataResponse;
import com.ahmadthesis.image.adapter.input.rest.image.v1.converter.ImageRestConverter;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.PaginationResponse;
import com.ahmadthesis.image.application.usecase.ImageService;
import com.ahmadthesis.image.application.usecase.PaymentService;
import com.ahmadthesis.image.domain.image.Image;
import com.ahmadthesis.image.domain.image.ProductLevel;
import com.ahmadthesis.image.domain.payment.PackageType;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component("ImageRegularHandler")
@RequiredArgsConstructor
public class ImageRegularHandler {

  private final ImageService imageService;
  private final PaymentService paymentService;

  Mono<ServerResponse> getImagesPagination(final ServerRequest request) {
    return ImageRestConverter.generatePaginationRequest(request)
        .flatMap(paginationRequest -> {
          final Flux<Image> imageFlux = imageService.getPublicImagesPagination(
              paginationRequest.getSize(),
              paginationRequest.getPage(),
              paginationRequest.getSortBy(),
              paginationRequest.getLatitude(),
              paginationRequest.getLongitude());

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
                  userId,
                  paginationRequest.getLatitude(),
                  paginationRequest.getLongitude());

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
            })
            .onErrorResume(IllegalArgumentException.class,
                throwable -> ServerResponse.badRequest().bodyValue(
                    new DataResponse<>(
                        null,
                        List.of(throwable.getMessage())
                    )
                ))
        )
        .onErrorResume(throwable -> ServerResponse.badRequest().bodyValue(
            new DataResponse<>(
                null,
                List.of(throwable.getMessage())
            )
        ));
  }

  Mono<ServerResponse> viewImageThumbnailFile(final ServerRequest request) {
    return ImageRestConverter.extractIdRequest(request)
        .flatMap(imageService::getPublicImageById)
        .flatMap(image -> {
          final File file = new File(image.getThumbnailImageDir());
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

  Mono<ServerResponse> viewImageFile(final ServerRequest request) {
    return ImageRestConverter.extractIdRequest(request)
        .flatMap(imageService::getImageById)
        .flatMap(image -> {
          final File file = new File(image.getOriginalImageDir());
          final Resource resource = new FileSystemResource(file);

          if (!image.getIsPublic()) {
            return ServerResponse.status(HttpStatus.NOT_FOUND).build();
          } else {
            return request.principal().flatMap(principal -> {
              final String username = principal.getName();
              return paymentService.getRecentPayment(username).flatMap(payment -> {
                if (image.getProductLevel().equals(ProductLevel.FREE)) {
                  return ServerResponse.ok()
                      .bodyValue(resource);
                } else if (payment.getPackageType().equals(PackageType.PRO) && image.getProductLevel()
                    .equals(ProductLevel.PRO)) {
                  return ServerResponse.ok()
                      .bodyValue(resource);
                } else if (payment.getPackageType().equals(PackageType.PREMIUM)) {
                  return ServerResponse.ok()
                      .bodyValue(resource);
                } else {
                  return ServerResponse.status(HttpStatus.NOT_FOUND).build();
                }
              });
            });
          }
        })
        .switchIfEmpty(Mono.defer(() -> ServerResponse.status(HttpStatus.NOT_FOUND).build()));
  }

  Mono<ServerResponse> viewPublicImageFile(final ServerRequest request) {
    return ImageRestConverter.extractIdRequest(request)
        .flatMap(imageService::getPublicImageById)
        .flatMap(image -> {
          final File file = new File(image.getOriginalImageDir());
          final Resource resource = new FileSystemResource(file);
          return ServerResponse.ok()
              .bodyValue(resource);
        })
        .switchIfEmpty(Mono.defer(() -> ServerResponse.status(HttpStatus.NOT_FOUND).build()));
  }
}
