package com.justahmed99.authapp.business;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;

public final class TokenConverter {
  public static Token fromKeycloakToToken(final Keycloak keycloak, final String username) {
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
        .username(username)
        .build();
  }

  public static Token fromAccessTokenResponse(final AccessTokenResponse accessTokenResponse) {
    return Token.builder()
        .accessToken(accessTokenResponse.getToken())
        .refreshToken(accessTokenResponse.getRefreshToken())
        .expiresIn(accessTokenResponse.getExpiresIn())
        .refreshExpiresIn(accessTokenResponse.getRefreshExpiresIn())
        .tokenType(accessTokenResponse.getTokenType())
        .idToken(accessTokenResponse.getIdToken())
        .notBeforePolicy(accessTokenResponse.getNotBeforePolicy())
        .sessionState(accessTokenResponse.getSessionState())
        .scope(accessTokenResponse.getScope())
        .build();
  }
}
