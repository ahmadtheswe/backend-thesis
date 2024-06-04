package com.ahmadthesis.image.domain.image;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenInfo {
  private String userId;
  private String email;
  private String preferredUsername;
  private String token;
}
