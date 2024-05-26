package com.justahmed99.authapp.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegistrationRequestDTO {

  private String username;
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
      message = "Password must be at least 8 characters long, "
          + "at least one digit, one lowercase letter, "
          + "one uppercase letter, and one special character.")
  private String password;
  private String firstName;
  private String lastName;
  private String email;
}

