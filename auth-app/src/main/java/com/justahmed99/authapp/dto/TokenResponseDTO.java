package com.justahmed99.authapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenResponseDTO {
  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;
  @JsonProperty("expires_in")
  private Long expiresIn;
  @JsonProperty("refresh_expires_in")
  private Long refreshExpiresIn;
  @JsonProperty("token_type")
  private String tokenType;
  @JsonProperty("id_token")
  private String idToken;
  @JsonProperty("not_before_policy")
  private Integer notBeforePolicy;
  @JsonProperty("session_state")
  private String sessionState;
  @JsonProperty("scope")
  private String scope;
  private String role;
  private String subscriptionLevel;
  private String email;
  private String username;
}
