package com.justahmed99.authapp.provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Generated
@Table("public.\"userinfo\"")
public class UserInfoEntity implements Persistable<String> {

  @Id
  private String id;
  private String username;
  private String email;
  private String role;
  private Long createdAt;
  private Boolean isActive;
  @Override
  public String getId() {
    return id;
  }

  @Override
  public boolean isNew() {
    return true;
  }
}
