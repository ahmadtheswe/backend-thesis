package com.ahmadthesis.auth.oauthclient.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {

  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String matchingPassword;
}
