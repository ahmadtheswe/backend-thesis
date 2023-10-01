package com.ahmadthesis.auth.keycloak_client.dao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
  private String accessToken;
  private String refreshToken;
  private Integer expiresIn;
  private Integer refreshExpiresIn;
  private String tokenType;
  private String idToken;
  private String sessionState;
  private String scope;
}
