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
@Table("public.\"ownership\"")
public class OwnershipEntity implements Persistable<String> {
  @Id
  private String id;
  private String imageId;
  private String ownerId;

  @Override
  public boolean isNew() {
    return true;
  }
}
