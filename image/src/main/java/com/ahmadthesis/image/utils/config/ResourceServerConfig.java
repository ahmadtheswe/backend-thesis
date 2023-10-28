package com.ahmadthesis.image.utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {
  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
    return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .cors(ServerHttpSecurity.CorsSpec::disable)
            .exceptionHandling(
                    exceptionHandlingSpec -> exceptionHandlingSpec.authenticationEntryPoint(
                            authenticationEntryPoint()
                    )
            )
            .authorizeExchange(authorizeExchangeSpec ->
                    authorizeExchangeSpec
                            .pathMatchers("/public/**").permitAll()
                            .pathMatchers("/testing/**").permitAll()
                            .pathMatchers("/image/v1/admin/**").hasRole("ADMIN")
                            .pathMatchers("/image/v1/regular/**").hasRole("REGULAR")
                            .anyExchange().authenticated())
            .oauth2ResourceServer(oAuth2ResourceServerSpec ->
                    oAuth2ResourceServerSpec.jwt(
                            jwtSpec -> jwtSpec.jwtAuthenticationConverter(jwtAuthenticationConverter())))
            .build();
  }

  @Bean
  public ServerAuthenticationEntryPoint authenticationEntryPoint() {
    return new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED);
  }

  @Bean
  public Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
    return new CustomJwtAuthenticationConverter();
  }
}
