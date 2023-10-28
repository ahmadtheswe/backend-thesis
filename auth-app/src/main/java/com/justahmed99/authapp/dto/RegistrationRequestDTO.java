package com.justahmed99.authapp.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegistrationRequestDTO {
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
}
