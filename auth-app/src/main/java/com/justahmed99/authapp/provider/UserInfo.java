package com.justahmed99.authapp.provider;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserInfo {
  private String id;
  private String username;
  private String email;
  private String role;
  private Long createdAt;
  private Boolean isActive;
}
