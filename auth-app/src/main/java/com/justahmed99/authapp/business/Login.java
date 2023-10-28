package com.justahmed99.authapp.business;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Login {
  private String username;
  private String email;
  private String password;
}
