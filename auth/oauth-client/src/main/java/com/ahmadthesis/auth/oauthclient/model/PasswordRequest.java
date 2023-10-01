package com.ahmadthesis.auth.oauthclient.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordRequest {

  private String email;
  private String oldPassword;
  private String newPassword;
}
