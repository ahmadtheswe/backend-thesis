package com.ahmadthesis.image.adapter.input.rest.image.v1.converter;

import com.ahmadthesis.image.adapter.input.rest.common.parser.FilePartParser;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.CartDeleteRequest;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.CartSaveRequest;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.ImageUploadResponse;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.PaginationRequest;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.SaveImageRequest;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.PaginationInfo;
import com.ahmadthesis.image.domain.image.Image;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Component
public final class ImageRestConverter {

  private static String UPLOAD_DIR;

  @Value("${directory.image}")
  public void setUploadDir(String UPLOAD_DIR) {
    ImageRestConverter.UPLOAD_DIR = UPLOAD_DIR;
  }

  public static ImageUploadResponse generateUploadResponse(Image image) {
    return ImageUploadResponse.builder()
        .id(image.getId())
        .title(image.getTitle())
        .build();
  }

  public static Mono<SaveImageRequest> extractUploadRequest(ServerRequest request) {
    return request.multipartData().flatMap(stringPartMultiValueMap -> {
      final FilePart image = (FilePart) stringPartMultiValueMap.getFirst("image");
      final Mono<String> titleMono = FilePartParser.parsePartToString(
          Objects.requireNonNull(stringPartMultiValueMap.getFirst("title")));
      final Mono<Boolean> isPublicMono = FilePartParser.parsePartToString(
              Objects.requireNonNull(stringPartMultiValueMap.getFirst("isPublic")))
          .map(Boolean::valueOf);
      final Mono<Long> priceIDRMono = FilePartParser.parsePartToString(
              Objects.requireNonNull(stringPartMultiValueMap.getFirst("priceIDR")))
          .map(Long::parseLong);
      final Mono<BigDecimal> latitudeMono = FilePartParser.parsePartToString(
              Objects.requireNonNull(stringPartMultiValueMap.getFirst("latitude")))
          .map(BigDecimal::new);
      final Mono<BigDecimal> longitudeMono = FilePartParser.parsePartToString(
              Objects.requireNonNull(stringPartMultiValueMap.getFirst("longitude")))
          .map(BigDecimal::new);

      final String mediaType = getExtensionByStringHandling(
          Objects.requireNonNull(image).filename());
      final String filename = UUID.randomUUID() + "." + getExtensionByStringHandling(mediaType);
      final String uploadDir = Paths.get(
          UPLOAD_DIR + File.separator + filename).toString();

      return Mono.zip(titleMono, isPublicMono, priceIDRMono, latitudeMono, longitudeMono)
          .flatMap(data ->
              Mono.just(
                  SaveImageRequest.builder()
                      .image(image)
                      .title(data.getT1())
                      .isPublic(data.getT2())
                      .filename(filename)
                      .mediaType(mediaType)
                      .uploadDir(uploadDir)
                      .priceIDR(data.getT3())
                      .latitude(data.getT4())
                      .longitude(data.getT5())
                      .build()));
    });
  }

  private static String getExtensionByStringHandling(String filename) {
    return filename.substring(filename.lastIndexOf(".") + 1);
  }

  public static Mono<PaginationRequest> generatePaginationRequest(ServerRequest request) {
    return Mono.just(
        PaginationRequest.builder()
            .size(request.queryParam("size").map(Integer::parseInt).orElse(5))
            .page(request.queryParam("page").map(Integer::parseInt).orElse(0))
            .sortBy(request.queryParam("sortBy").orElse("id"))
            .build()
    );
  }

  public static PaginationInfo generatePaginationInfo(
      PaginationRequest paginationRequest,
      Long totalItems) {

    return PaginationInfo.builder()
        .currentPage(paginationRequest.getPage())
        .pageSize(paginationRequest.getSize())
        .sortBy(paginationRequest.getSortBy())
        .totalPages((totalItems + paginationRequest.getSize() - 1) / paginationRequest.getSize())
        .totalItems(totalItems)
        .build();
  }

  public static Mono<String> extractIdRequest(ServerRequest request) {
    return Mono.just(request.queryParam("id").orElse(""));
  }

  public static Mono<CartDeleteRequest> getCartId(ServerRequest request) {
    return request.bodyToMono(CartDeleteRequest.class);
  }

  public static Mono<CartSaveRequest> getImageId(ServerRequest request) {
    return request.bodyToMono(CartSaveRequest.class);
  }
}
