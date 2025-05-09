package com.ahmadthesis.image.adapter.input.rest.image.v1.converter;

import com.ahmadthesis.image.adapter.input.rest.common.parser.FilePartParser;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.PaginationRequest;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.SaveImageRequest;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.preorder.BBoxRequest;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.preorder.PreOrderCallBackRequest;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.preorder.PreOrderRequest;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.ImageUploadResponse;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.PaginationInfo;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.preorder.BBoxResponse;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.preorder.CoordinateResponse;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.preorder.PreOrderResponse;
import com.ahmadthesis.image.domain.image.BBox;
import com.ahmadthesis.image.domain.image.Coordinate;
import com.ahmadthesis.image.domain.image.Image;
import com.ahmadthesis.image.domain.image.PreOrder;
import com.ahmadthesis.image.domain.image.TokenInfo;
import com.ahmadthesis.image.global.utils.DateUtils;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Component
public final class ImageRestConverter {

  private static String UPLOAD_DIR;
  private static String THUMBNAIL_DIR;

  @Value("${directory.image}")
  public void setUploadDir(String UPLOAD_DIR) {
    ImageRestConverter.UPLOAD_DIR = UPLOAD_DIR;
  }

  @Value("${directory.thumbnail}")
  public void setThumbnailDir(String THUMBNAIL_DIR) {
    ImageRestConverter.THUMBNAIL_DIR = THUMBNAIL_DIR;
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
      final FilePart thumbnail = (FilePart) stringPartMultiValueMap.getFirst("thumbnail");
      final Mono<String> titleMono = FilePartParser.parsePartToString(
          Objects.requireNonNull(stringPartMultiValueMap.getFirst("title")));
      final Mono<Boolean> isPublicMono = FilePartParser.parsePartToString(
              Objects.requireNonNull(stringPartMultiValueMap.getFirst("isPublic")))
          .map(Boolean::valueOf);
      final Mono<BigDecimal> latitudeMono = FilePartParser.parsePartToString(
              Objects.requireNonNull(stringPartMultiValueMap.getFirst("latitude")))
          .map(BigDecimal::new);
      final Mono<BigDecimal> longitudeMono = FilePartParser.parsePartToString(
              Objects.requireNonNull(stringPartMultiValueMap.getFirst("longitude")))
          .map(BigDecimal::new);
      final Mono<String> productLevelMono = FilePartParser.parsePartToString(
          Objects.requireNonNull(stringPartMultiValueMap.getFirst("productLevel")));

      final String mediaType = getExtensionByStringHandling(
          Objects.requireNonNull(image).filename());
      final String filename = UUID.randomUUID() + "." + getExtensionByStringHandling(mediaType);
      final String uploadDir = Paths.get(
          UPLOAD_DIR + File.separator + filename).toString();
      final String thumbnailDir = Paths.get(
          THUMBNAIL_DIR + File.separator + filename).toString();

      // ............... (additional code) ...............

      return Mono.zip(titleMono, isPublicMono, latitudeMono, longitudeMono, productLevelMono)
          .flatMap(data ->
              Mono.just(
                  SaveImageRequest.builder()
                      .image(image)
                      .thumbnail(thumbnail)
                      .title(data.getT1())
                      .isPublic(data.getT2())
                      .filename(filename)
                      .mediaType(mediaType)
                      .uploadDir(uploadDir)
                      .thumbnailDir(thumbnailDir)
                      .latitude(data.getT3())
                      .longitude(data.getT4())
                      .productLevel(data.getT5())
                      .build()));
    });
  }

  private static String getExtensionByStringHandling(String filename) {
    return filename.substring(filename.lastIndexOf(".") + 1);
  }

  public static Mono<PaginationRequest> generatePaginationRequest(ServerRequest request) {
    if (request.queryParam("longitude").isPresent() &&
        request.queryParam("latitude").isEmpty()) {
      throw new IllegalArgumentException("Latitude must not be null");
    } else if (request.queryParam("longitude").isEmpty()
        && request.queryParam("latitude").isPresent()) {
      throw new IllegalArgumentException("longitude must not be null");
    } else {
      return Mono.just(
          PaginationRequest.builder()
              .size(request.queryParam("size").map(Integer::parseInt).orElse(5))
              .page(request.queryParam("page").map(Integer::parseInt).orElse(0))
              .title(request.queryParam("title").orElse(null))
              .radius(request.queryParam("radius").map(Double::parseDouble).orElse(5.0))
              .sortBy(request.queryParam("sortBy").orElse("id"))
              .latitude(
                  request.queryParam("latitude").map(BigDecimal::new).orElse(new BigDecimal(0)))
              .longitude(
                  request.queryParam("longitude").map(BigDecimal::new).orElse(new BigDecimal(0)))
              .build()
      );
    }
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

  public static String extractUserId(ServerRequest request) {
    return request.headers().header("Authorization").stream()
        .findFirst()
        .map(headerValue -> {
          String token = headerValue.replace("Bearer ", "");
          Jwt jwt = parseJwt(token);
          JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jwt);
          return jwtAuthenticationToken.getToken()
              .getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);
        }).orElse(null);
  }

  private static Jwt parseJwt(String token) {
    return Jwt.withTokenValue(token).build();
  }

  public static PreOrderResponse generatePreOrderResponse(final PreOrder preOrder) {
    return PreOrderResponse.builder()
        .id(preOrder.getId())
        .bBoxResponse(BBoxResponse.builder()
            .maxLongitude(preOrder.getBBox().getMaxLongitude())
            .maxLatitude(preOrder.getBBox().getMaxLatitude())
            .minLongitude(preOrder.getBBox().getMinLongitude())
            .minLatitude(preOrder.getBBox().getMinLatitude())
            .build())
        .centerCoordinateResponse(CoordinateResponse.builder()
            .latitude(preOrder.getCenterCoordinate().getLatitude())
            .longitude(preOrder.getCenterCoordinate().getLongitude())
            .build())
        .isActive(preOrder.getIsActive())
        .createdAt(preOrder.getCreatedAt() == null ? null
            : DateUtils.millisecondsToDateString(preOrder.getCreatedAt()))
        .deliveredAt(preOrder.getDeliveredAt() == null ? null
            : DateUtils.millisecondsToDateString(preOrder.getDeliveredAt()))
        .isPaid(preOrder.getIsPaid())
        .redirectUrl(preOrder.getIsPaid() ? null : preOrder.getRedirectUrl())
        .probeType(preOrder.getProbeType())
        .build();
  }

  public static Mono<PreOrderRequest> generatePreOrderRequest(final ServerRequest request) {
    return request.bodyToMono(PreOrderRequest.class);
  }

  public static Mono<BBoxRequest> generateBBoxRequest(final ServerRequest request) {
    return request.bodyToMono(BBoxRequest.class);
  }

  public static Mono<PreOrderCallBackRequest> generatePreorderCallBackRequest(
      final ServerRequest request) {
    return request.bodyToMono(PreOrderCallBackRequest.class);
  }

  public static Mono<PreOrder> generatePreOrderFromRequest(final PreOrderRequest preOrderRequest,
      final TokenInfo tokenInfo) {
    return Mono.just(PreOrder.builder()
        .requesterId(tokenInfo.getUserId())
        .requesterEmail(tokenInfo.getEmail())
        .requesterUsername(tokenInfo.getPreferredUsername())
        .bBox(BBox.builder()
            .maxLongitude(preOrderRequest.getBBox().getMaxLongitude())
            .maxLatitude(preOrderRequest.getBBox().getMaxLatitude())
            .minLongitude(preOrderRequest.getBBox().getMinLongitude())
            .minLatitude(preOrderRequest.getBBox().getMinLatitude())
            .build())
        .isActive(true)
        .centerCoordinate(Coordinate.builder()
            .latitude(preOrderRequest.getBBox().getMinLatitude().add(preOrderRequest.getBBox()
                .getMaxLatitude()).divide(new BigDecimal(2), RoundingMode.HALF_UP))
            .longitude(preOrderRequest.getBBox().getMinLongitude().add(preOrderRequest.getBBox()
                .getMaxLongitude()).divide(new BigDecimal(2), RoundingMode.HALF_UP))
            .build())
        .createdAt(DateUtils.now())
        .build());
  }
}
