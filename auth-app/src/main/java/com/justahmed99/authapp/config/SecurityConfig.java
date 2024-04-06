package com.justahmed99.authapp.config;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
    return http
        .csrf(CsrfSpec::disable)
        .cors(corsSpec -> corsSpec.configurationSource(corsConfigurationSource()))
        .exceptionHandling(
            exceptionHandlingSpec -> exceptionHandlingSpec.authenticationEntryPoint(
                authenticationEntryPoint()
            )
        )
        .authorizeExchange(authorizeExchangeSpec ->
            authorizeExchangeSpec
                .pathMatchers("/api/user/registration/**").permitAll()
                .pathMatchers("/api/user/login/**").permitAll()
                .pathMatchers("/api/user/refresh/**").permitAll()
                .pathMatchers("/api/user/activate/**").permitAll()
                .pathMatchers("/api/user/logout/**").authenticated()
                .pathMatchers("/public/**").permitAll()
                .pathMatchers("/api/user/admin/**").hasRole("ADMIN")
                .pathMatchers("/regular/**").hasRole("REGULAR")
                .anyExchange().authenticated())
        .oauth2Login(Customizer.withDefaults())
        .oauth2Client(Customizer.withDefaults())
        .oauth2ResourceServer(oAuth2ResourceServerSpec ->
            oAuth2ResourceServerSpec.jwt(
                jwtSpec -> jwtSpec.jwtAuthenticationConverter(jwtAuthenticationConverter())))
        .logout(Customizer.withDefaults())
        .build();
  }

  @Bean
  public ServerAuthenticationEntryPoint authenticationEntryPoint() {
    return new HttpStatusServerEntryPoint(HttpStatus.FORBIDDEN);
  }

  @Bean
  public Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
    return new CustomJwtAuthenticationConverter();
  }

  private CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(Collections.singletonList("*"));
    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(Collections.singletonList("*"));
    source.registerCorsConfiguration("/**", config);
    return source;
  }

}
