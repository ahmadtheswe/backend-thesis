package com.justahmed99.authapp.business;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Token {
  private String accessToken;
  private String refreshToken;
  private Long expiresIn;
  private Long refreshExpiresIn;
  private String tokenType;
  private String idToken;
  private Integer notBeforePolicy;
  private String sessionState;
  private String scope;
}
