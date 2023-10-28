package com.ahmadthesis.image.adapter.input.rest.image.v1.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

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
                    .and(accept(MediaType.APPLICATION_JSON)), handler::getUserImagesCollectionPagination);
  }
}
