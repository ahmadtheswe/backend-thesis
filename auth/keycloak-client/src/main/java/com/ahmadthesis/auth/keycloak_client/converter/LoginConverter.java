package com.ahmadthesis.auth.keycloak_client.converter;

import com.ahmadthesis.auth.keycloak_client.dao.KeycloakTokenResponse;
import com.ahmadthesis.auth.keycloak_client.dao.LoginResponse;
import org.springframework.stereotype.Component;

@Component
public class LoginConverter {
  public LoginResponse convert(final KeycloakTokenResponse keycloakLoginResponse) {
    return LoginResponse.builder()
        .accessToken(keycloakLoginResponse.getAccessToken())
        .refreshToken(keycloakLoginResponse.getRefreshToken())
        .expiresIn(keycloakLoginResponse.getExpiresIn())
        .refreshExpiresIn(keycloakLoginResponse.getRefreshExpiresIn())
        .tokenType(keycloakLoginResponse.getTokenType())
        .idToken(keycloakLoginResponse.getAccessToken())
        .sessionState(keycloakLoginResponse.getSessionState())
        .scope(keycloakLoginResponse.getScope())
        .build();
  }
}
