package com.ahmadthesis.image.adapter.input.rest.image.v1.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration("ImageAdminRouter")
@RequiredArgsConstructor
public class ImageAdminRouter {

  private final ImageAdminHandler handler;

  @Bean
  RouterFunction<ServerResponse> imageAdminRouters() {
    return RouterFunctions
        .route(POST("/image/v1/admin")
            .and(accept(MediaType.MULTIPART_FORM_DATA)), handler::uploadImage)
        .andRoute(GET("/image/v1/admin/detail")
            .and(accept(MediaType.APPLICATION_JSON)), handler::getImageById)
        .andRoute(GET("/image/v1/admin")
            .and(accept(MediaType.APPLICATION_JSON)), handler::getImagesPagination)
        .andRoute(GET("/image/v1/admin/view")
            .and(accept(MediaType.APPLICATION_OCTET_STREAM)), handler::viewImageFile)
        .andRoute(GET("/image/v1/admin/view/thumbnail")
            .and(accept(MediaType.APPLICATION_OCTET_STREAM)), handler::viewImageThumbnailFile)
        .andRoute(GET("/image/v1/admin/user-detail")
            .and(accept(MediaType.APPLICATION_JSON)), handler::handleRequest)
        .andRoute(GET("/image/v1/admin/pre-order")
            .and(accept(MediaType.APPLICATION_JSON)), handler::getPreOrderList);
  }
}
