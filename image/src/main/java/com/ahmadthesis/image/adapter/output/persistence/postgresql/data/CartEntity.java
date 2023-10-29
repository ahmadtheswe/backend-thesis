package com.ahmadthesis.image.adapter.output.persistence.postgresql.data;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Generated
@Table("public.\"user_cart\"")
public class CartEntity implements Persistable<String> {
  @Id
  private String id;
  private String imageId;
  private String userId;
  private Long createdAt;
  private Boolean isActive;
  @Override
  public boolean isNew() {
    return true;
  }
}
