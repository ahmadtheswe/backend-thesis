package com.ahmadthesis.image.adapter.input.rest.image.v1.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration("ImageRegularRouter")
@RequiredArgsConstructor
public class ImageRegularRouter {

  private final ImageRegularHandler handler;

  @Bean
  RouterFunction<ServerResponse> imageRegularRouters() {
    return RouterFunctions
        .route(GET("/image/v1/regular")
            .and(accept(MediaType.APPLICATION_JSON)), handler::getImagesPagination)
        .andRoute(GET("/image/v1/regular/collection")
            .and(accept(MediaType.APPLICATION_JSON)), handler::getUserImagesCollectionPagination)
        .andRoute(GET("/image/v1/regular/view")
            .and(accept(MediaType.APPLICATION_OCTET_STREAM)), handler::viewImageFile)
        .andRoute(GET("/image/v1/regular/cart")
            .and(accept(MediaType.APPLICATION_JSON)), handler::getUserCart)
        .andRoute(POST("/image/v1/regular/cart")
            .and(accept(MediaType.APPLICATION_JSON)), handler::saveCart)
        .andRoute(DELETE("/image/v1/regular/cart")
            .and(accept(MediaType.APPLICATION_JSON)), handler::deleteCart)
        .andRoute(GET("/image/v1/regular/on-sale/view")
            .and(accept(MediaType.APPLICATION_JSON)), handler::viewPublicImageFile);
  }
}
