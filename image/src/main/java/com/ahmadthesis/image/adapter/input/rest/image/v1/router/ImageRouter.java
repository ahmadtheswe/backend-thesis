package com.ahmadthesis.image.adapter.input.rest.image.v1.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration("ImageRouter")
public class ImageRouter {
  private final ImageHandler handler;

  public ImageRouter(
          ImageHandler handler
  ) {
    this.handler = handler;
  }

  @Bean
  RouterFunction<ServerResponse> imageRouters() {
    return RouterFunctions
            .route(POST("/image/v1")
                    .and(accept(MediaType.MULTIPART_FORM_DATA)), handler::uploadImage)
            .andRoute(GET("/image/v1/detail")
                    .and(accept(MediaType.APPLICATION_JSON)), handler::getImageById)
            .andRoute(GET("/image/v1")
                    .and(accept(MediaType.APPLICATION_JSON)), handler::getImagesPagination)
            .andRoute(GET("/image/v1/view")
                    .and(accept(MediaType.APPLICATION_OCTET_STREAM)), handler::viewImageFile);
  }
}
