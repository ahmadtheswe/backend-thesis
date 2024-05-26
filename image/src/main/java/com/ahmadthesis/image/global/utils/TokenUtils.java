package com.ahmadthesis.image.global.utils;

import com.ahmadthesis.image.domain.image.TokenInfo;
import java.security.Principal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class TokenUtils {
  public static TokenInfo generateTokenInfo(Principal principal) {
    JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) principal;
    Jwt jwt = jwtToken.getToken();

    return TokenInfo.builder()
        .userId(jwt.getSubject())
        .email(jwt.getClaimAsString("email"))
        .preferredUsername(jwt.getClaimAsString("preferred_username"))
        .build();
  }
}
