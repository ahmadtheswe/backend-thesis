package com.justahmed99.authapp.provider;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserInfo {
  private String id;
  private String username;
  private String email;
  private String role;
  private LocalDateTime createdAt;
  private Boolean isActive;
}
