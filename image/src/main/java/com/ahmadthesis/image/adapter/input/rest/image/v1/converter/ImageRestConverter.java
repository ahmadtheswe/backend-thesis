package com.ahmadthesis.image.adapter.input.rest.image.v1.converter;

import com.ahmadthesis.image.adapter.input.rest.common.parser.FilePartParser;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.CartDeleteRequest;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.CartSaveRequest;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.ImageUploadResponse;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.PaginationRequest;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.SaveImageRequest;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.PaginationInfo;
import com.ahmadthesis.image.domain.image.Image;
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

  @Value("${directory.image}")
  private static String UPLOAD_DIR;

  public static ImageUploadResponse generateUploadResponse(Image image) {
    return ImageUploadResponse.builder()
            .id(image.getId())
            .title(image.getTitle())
            .build();
  }

  public static Mono<SaveImageRequest> extractUploadRequest(ServerRequest request) {
    return request.multipartData().flatMap(stringPartMultiValueMap -> {
      FilePart image = (FilePart) stringPartMultiValueMap.getFirst("image");
      Mono<String> titleMono = FilePartParser.parsePartToString(
              Objects.requireNonNull(stringPartMultiValueMap.getFirst("title")));
      Mono<Boolean> isPublicMono = FilePartParser.parsePartToString(
                      Objects.requireNonNull(stringPartMultiValueMap.getFirst("isPublic")))
              .map(Boolean::valueOf);
      String mediaType = getExtensionByStringHandling(Objects.requireNonNull(image).filename());
      String filename = UUID.randomUUID() + "." + getExtensionByStringHandling(mediaType);
      String uploadDir = Paths.get(UPLOAD_DIR + File.separator + filename).toString();

      return titleMono.flatMap(title ->
              isPublicMono.map(isPublic ->
                      SaveImageRequest.builder()
                              .image(image)
                              .title(title)
                              .isPublic(isPublic)
                              .filename(filename)
                              .mediaType(mediaType)
                              .uploadDir(uploadDir)
                              .build()
              )

      );
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
