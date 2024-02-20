package com.justahmed99.authapp.dto.converter;

import com.justahmed99.authapp.business.Token;
import com.justahmed99.authapp.dto.TokenResponseDTO;

public final class TokenDTOConverter {
  public static TokenResponseDTO fromTokenToTokenDTO(Token token) {
    return TokenResponseDTO.builder()
        .accessToken(token.getAccessToken())
        .refreshToken(token.getRefreshToken())
        .expiresIn(token.getExpiresIn())
        .refreshExpiresIn(token.getRefreshExpiresIn())
        .tokenType(token.getTokenType())
        .idToken(token.getIdToken())
        .notBeforePolicy(token.getNotBeforePolicy())
        .sessionState(token.getSessionState())
        .scope(token.getScope())
        .role(token.getRole())
        .subscriptionLevel(token.getSubscriptionLevel())
        .build();
  }
}
