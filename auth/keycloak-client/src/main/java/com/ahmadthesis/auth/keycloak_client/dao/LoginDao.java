package com.ahmadthesis.auth.keycloak_client.dao;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginDao {
  private String username;
  private String password;
}
