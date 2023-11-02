package com.justahmed99.authapp.business;

import org.keycloak.admin.client.Keycloak;

public final class TokenConverter {
  public static Token fromKeycloakToToken(final Keycloak keycloak) {
    return Token.builder()
        .accessToken(keycloak.tokenManager().getAccessToken().getToken())
        .refreshToken(keycloak.tokenManager().refreshToken().getToken())
        .expiresIn(keycloak.tokenManager().getAccessToken().getExpiresIn())
        .refreshExpiresIn(keycloak.tokenManager().refreshToken().getExpiresIn())
        .tokenType(keycloak.tokenManager().getAccessToken().getTokenType())
        .idToken(keycloak.tokenManager().getAccessToken().getIdToken())
        .notBeforePolicy(keycloak.tokenManager().getAccessToken().getNotBeforePolicy())
        .sessionState(keycloak.tokenManager().getAccessToken().getSessionState())
        .scope(keycloak.tokenManager().getAccessToken().getScope())
        .build();
  }
}
