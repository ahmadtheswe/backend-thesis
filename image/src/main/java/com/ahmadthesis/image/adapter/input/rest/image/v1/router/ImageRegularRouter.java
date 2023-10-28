package com.ahmadthesis.image.adapter.input.rest.image.v1.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration("ImageRegularRouter")
@RequiredArgsConstructor
public class ImageRegularRouter {
  private final ImageRegularHandler handler;
}
