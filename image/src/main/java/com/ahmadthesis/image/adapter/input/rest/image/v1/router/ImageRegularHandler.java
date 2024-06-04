package com.ahmadthesis.image.adapter.input.rest.image.v1.router;

import com.ahmadthesis.image.adapter.input.rest.common.dto.DataResponse;
import com.ahmadthesis.image.adapter.input.rest.image.v1.converter.ImageRestConverter;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.ImageResponse;
import com.ahmadthesis.image.adapter.input.rest.image.v1.dto.response.PaginationResponse;
import com.ahmadthesis.image.adapter.output.webclient.data.PreOrderTransactionDTO;
import com.ahmadthesis.image.application.usecase.CopernicusWebClientService;
import com.ahmadthesis.image.application.usecase.EmailService;
import com.ahmadthesis.image.application.usecase.ImageService;
import com.ahmadthesis.image.application.usecase.PaymentWebClientService;
import com.ahmadthesis.image.domain.image.BBox;
import com.ahmadthesis.image.domain.image.Email;
import com.ahmadthesis.image.domain.image.Image;
import com.ahmadthesis.image.domain.image.ProductLevel;
import com.ahmadthesis.image.domain.payment.PackageType;
import com.ahmadthesis.image.global.utils.DateUtils;
import com.ahmadthesis.image.global.utils.TokenUtils;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component("ImageRegularHandler")
@RequiredArgsConstructor
public class ImageRegularHandler {

  private final ImageService imageService;
  private final PaymentWebClientService paymentWebClientService;
  private final CopernicusWebClientService copernicusWebClientService;
  private final EmailService emailService;

  @Value("${directory.image}")
  private String UPLOAD_DIRECTORY;

  @Value("${midtrans.server-key}")
  private String MIDTRANS_SERVER_KEY;

  Mono<ServerResponse> getImagesPagination(final ServerRequest request) {
    return ImageRestConverter.generatePaginationRequest(request)
        .flatMap(paginationRequest -> {
          final Flux<Image> imageFlux = imageService.getPublicImagesPagination(
              paginationRequest.getSize(),
              paginationRequest.getPage(),
              paginationRequest.getSortBy(),
              paginationRequest.getLatitude(),
              paginationRequest.getLongitude(),
              paginationRequest.getRadius()
          );

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
              final Authentication authentication = (Authentication) principal;
              final String jwtToken = extractJwtToken(authentication);
              return paymentWebClientService.getActivePackage(jwtToken).flatMap(productLevel -> {
                if (image.getProductLevel().equals(ProductLevel.FREE)) {
                  return ServerResponse.ok()
                      .bodyValue(resource);
                } else if (productLevel.equals(PackageType.PRO.getName()) && image.getProductLevel()
                    .equals(ProductLevel.PRO)) {
                  return ServerResponse.ok()
                      .bodyValue(resource);
                } else if (productLevel.equals(PackageType.PREMIUM.getName())) {
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

  private String extractJwtToken(Authentication authentication) {
    try {
      Jwt credentials = (Jwt) authentication.getCredentials();
      return credentials.getTokenValue();
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "Unable to extract JWT token from authentication credentials.", e);
    }
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

  Mono<ServerResponse> getImageById(final ServerRequest request) {
    final String imageId = request.queryParam("id").orElse(null);
    if (imageId == null) {
      return ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(
          new DataResponse<>(null, List.of("Image ID should not be null"))
      );
    }
    return imageService.getPublicImageById(imageId)
        .flatMap(image -> ServerResponse.ok().bodyValue(
            new DataResponse<>(
                ImageResponse.builder()
                    .title(image.getTitle())
                    .mediaType(image.getMediaType())
                    .filename(image.getFilename())
                    .productLevel(image.getProductLevel().name())
                    .latitude(image.getLatitude())
                    .longitude(image.getLongitude())
                    .build(),
                new ArrayList<>()
            )
        )).switchIfEmpty(Mono.defer(() -> ServerResponse.status(HttpStatus.NOT_FOUND).build()));
  }

  Mono<ServerResponse> savePreOrderRequest(final ServerRequest request) {
    return request.principal().flatMap(principal -> {
          return Mono.just(TokenUtils.generateTokenInfo(principal));
        })
        .flatMap(tokenInfo -> ImageRestConverter.generatePreOrderRequest(request)
            .flatMap(
                preOrderRequest -> {
                  return ImageRestConverter.generatePreOrderFromRequest(preOrderRequest,
                          tokenInfo)
//                      .flatMap(imageService::savePreOrder)
                      .flatMap(preOrder -> {
                        return paymentWebClientService.chargePreOrder(
                            PreOrderTransactionDTO.builder()
                                .email(tokenInfo.getEmail())
                                .imageSize(preOrderRequest.getImageSize())
                                .build(),
                            tokenInfo.getToken()
                        ).flatMap(chargeDTO -> {
                          preOrder.setPaymentPreorderId(chargeDTO.getOrderId());
                          preOrder.setIsNew(true);
                          return imageService.savePreOrder(preOrder)
                              .flatMap(preOrder1 -> {
                                return Mono.just(chargeDTO);
                              });
                        });
                      })
                      .flatMap(chargeDTO -> {
                        Mono<Void> sendEmailMono = emailService.sendEmail(
                            Email.builder()
                                .to(tokenInfo.getEmail())
                                .subject("Pre Order Confirmation")
                                .content(chargeDTO.getRedirectUrl())
                                .build()
                        ).subscribeOn(Schedulers.boundedElastic());
                        sendEmailMono.subscribe();

                        return Mono.just(chargeDTO);

                      })
                      .flatMap(preOrderTransactionDTO -> {
                        return ServerResponse.ok()
                            .bodyValue(new DataResponse<>(preOrderTransactionDTO, List.of()));
                      });
                }));
//        )
//        .flatMap(imageService::savePreOrder);
//        .doOnSuccess(preOrder -> {
//          Mono<byte[]> generatedImage = copernicusWebClientService.generateImage(
//                  BBox.builder().minLongitude(preOrder.getBBox().getMinLongitude())
//                      .minLatitude(preOrder.getBBox().getMinLatitude())
//                      .maxLongitude(preOrder.getBBox().getMaxLongitude())
//                      .maxLatitude(preOrder.getBBox().getMaxLatitude()).build())
//              .flatMap(Mono::just);
//
//          generatedImage.flatMap(
//              bytes -> saveBytesToFile(bytes, preOrder.getId()).thenReturn(preOrder)).subscribeOn(
//              Schedulers.boundedElastic()).subscribe();
//        })
//        .then(Mono.defer(() -> ServerResponse.ok()
//            .bodyValue(new DataResponse<>(null, List.of()))));
  }

  public Mono<ServerResponse> preorderCallBack(ServerRequest request) {
    return ImageRestConverter.generatePreorderCallBackRequest(request)
        .flatMap(preOrderCallBackRequest -> {
          return imageService.getPreOrderByPreorderId(preOrderCallBackRequest.getPreorderId())
              .flatMap(preOrder -> {
                String paymentSHA = Sha512DigestUtils.shaHex(
                    preOrder.getPaymentPreorderId() + preOrder.getRequesterEmail()
                        + preOrder.getRequesterUsername() + MIDTRANS_SERVER_KEY);

                if (!paymentSHA.equals(preOrderCallBackRequest.getPreorderSignature())) {
                  return ServerResponse.badRequest()
                      .bodyValue(new DataResponse<>(null, List.of("Invalid signature key")));
                }

                preOrder.setDeliveredAt(DateUtils.now());
                preOrder.setIsNew(false);

                return imageService.savePreOrder(preOrder)
                    .doOnSuccess(preOrderImage -> {
                      Mono<byte[]> generatedImage = copernicusWebClientService.generateImage(
                              BBox.builder().minLongitude(preOrderImage.getBBox().getMinLongitude())
                                  .minLatitude(preOrderImage.getBBox().getMinLatitude())
                                  .maxLongitude(preOrderImage.getBBox().getMaxLongitude())
                                  .maxLatitude(preOrderImage.getBBox().getMaxLatitude()).build())
                          .flatMap(Mono::just);

                      generatedImage.flatMap(
                              bytes -> saveBytesToFile(bytes, preOrderImage.getId()).thenReturn(preOrderImage))
                          .subscribeOn(
                              Schedulers.boundedElastic()).subscribe();
                    })
                    .then(Mono.defer(() -> ServerResponse.ok()
                        .bodyValue(new DataResponse<>(null, List.of()))));
              });
        })
        .switchIfEmpty(ServerResponse.badRequest()
            .bodyValue(new DataResponse<>(null, List.of("Preorder not found"))));
  }

  private Mono<Void> saveBytesToFile(byte[] bytes, final String preOrderId) {
    return Mono.fromCallable(() -> {
      // Ensure the upload directory exists
      Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }

      // Create a path for the file
      String filename = preOrderId + ".png";  // You can generate a unique filename if needed
      Path filePath = uploadPath.resolve(filename);

      // Write the bytes to the file
      Files.write(filePath, bytes);
      return null;
    }).then();
  }

  Mono<ServerResponse> getPreOrderList(final ServerRequest request) {
    return request.principal().flatMap(principal -> Mono.just(principal.getName()))
        .flatMap(userId -> imageService.getPreOrderList(userId)
            .map(ImageRestConverter::generatePreOrderResponse)
            .collectList()
            .flatMap(preOrderResponses -> ServerResponse.ok()
                .bodyValue(new DataResponse<>(preOrderResponses, List.of()))));
  }

  Mono<ServerResponse> generateImage(final ServerRequest request) {
    return ImageRestConverter.generateBBoxRequest(request)
        .flatMap(bBoxRequest -> copernicusWebClientService.generateImage(
                BBox.builder().minLongitude(bBoxRequest.getMinLongitude())
                    .minLatitude(bBoxRequest.getMinLatitude())
                    .maxLongitude(bBoxRequest.getMaxLongitude())
                    .maxLatitude(bBoxRequest.getMaxLatitude()).build())
            .flatMap(bytes -> ServerResponse.ok().bodyValue(bytes)));
  }
}
