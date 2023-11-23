package com.ahmadthesis.image.adapter.input.rest.image.v1.router;

import com.ahmadthesis.image.adapter.input.rest.common.dto.DataResponse;
import com.ahmadthesis.image.adapter.input.rest.image.v1.converter.ImageRestConverter;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.PaginationResponse;
import com.ahmadthesis.image.application.usecase.CartService;
import com.ahmadthesis.image.application.usecase.ImageService;
import com.ahmadthesis.image.application.usecase.OwnershipService;
import com.ahmadthesis.image.domain.image.Cart;
import com.ahmadthesis.image.domain.image.Image;
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

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

@Component("ImageRegularHandler")
@RequiredArgsConstructor
public class ImageRegularHandler {

  private final ImageService imageService;
  private final CartService cartService;
  private final OwnershipService ownershipService;

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

  Mono<ServerResponse> getUserCart(final ServerRequest request) {
    return request.principal().flatMap(principal -> Mono.just(principal.getName()))
        .flatMap(userId -> cartService.getUserChartList(userId).collectList()
            .flatMap(carts -> ServerResponse.ok().bodyValue(
                new DataResponse<>(
                    carts,
                    new ArrayList<>()
                )
            )));
  }

  Mono<ServerResponse> saveCart(final ServerRequest request) {
    return request.principal().flatMap(principal -> Mono.just(principal.getName()))
        .flatMap(userId -> ImageRestConverter.getImageId(request)
            .flatMap(cartSaveRequest -> cartService.save(
                Cart.builder()
                    .id(UUID.randomUUID().toString())
                    .imageId(cartSaveRequest.getImageId())
                    .userId(userId)
                    .build()))
            .flatMap(cart -> ServerResponse.ok().bodyValue(
                new DataResponse<>(
                    cart,
                    new ArrayList<>()
                ))));
  }

  Mono<ServerResponse> deleteCart(final ServerRequest request) {
    return request.principal().flatMap(principal -> Mono.just(principal.getName()))
        .flatMap(userId -> ImageRestConverter.getCartId(request)
            .flatMap(
                cartDeleteRequest -> cartService.deleteChart(userId, cartDeleteRequest.getCartId())
                    .then(Mono.defer(Mono::empty))));
  }

  Mono<ServerResponse> viewImageFile(final ServerRequest request) {
    return ImageRestConverter.extractIdRequest(request)
        .flatMap(imageId -> request.principal().flatMap(principal -> Mono.just(principal.getName()))
            .flatMap(
                userId -> ownershipService.getImageByOwnershipByOwnerIdAndUserId(userId, imageId))
            .flatMap(imageOwnership -> imageService.getImageById(imageOwnership.getId()))
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
            ))));

  }
}
