package com.justahmed99.authapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.WebFilter;

//@Configuration
@RequiredArgsConstructor
public class WebFluxConfig implements WebFluxConfigurer {
  private final AllowedIPFilter allowedIPFilter;

  @Bean
  public WebFilter webFilter() {
    return allowedIPFilter;
  }
}
