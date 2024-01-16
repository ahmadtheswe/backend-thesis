package com.ahmadthesis.payment.provider;

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
@Table("public.\"package\"")
public class PackageEntity implements Persistable<String> {
  @Id
  private String id;
  private String packageName;
  private Long price;
  private Boolean isActive;
  @Override
  public boolean isNew() {
    return true;
  }
}