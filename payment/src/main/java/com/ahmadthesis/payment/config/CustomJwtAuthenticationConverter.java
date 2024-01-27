package com.ahmadthesis.payment.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

public class CustomJwtAuthenticationConverter implements
        Converter<Jwt, Mono<AbstractAuthenticationToken>> {

  @SuppressWarnings("unchecked")
  @Override
  public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
    Map<String, Object> claims = jwt.getClaims();
    Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");

    List<String> roles = new ArrayList<>();
    if (realmAccess != null) {
      roles = (List<String>) realmAccess.get("roles");
    }

    List<GrantedAuthority> authorities = roles.stream()
            .map(roleName -> "ROLE_" + roleName.toUpperCase())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    return Mono.just(new JwtAuthenticationToken(jwt, authorities));
  }
}