package com.justahmed99.authapp.provider;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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
  private LocalDateTime createdAt;
  private Boolean isActive;

  @Transient
  private boolean isNew = true;

  public void setIsNew(boolean isNew) { this.isNew = isNew; }
  public boolean isNew() { return isNew; }

  @Override
  public String getId() {
    return id;
  }
}
