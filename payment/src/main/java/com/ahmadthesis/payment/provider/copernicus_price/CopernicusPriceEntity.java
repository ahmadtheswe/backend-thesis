package com.ahmadthesis.payment.provider.copernicus_price;

import java.math.BigInteger;
import java.time.Instant;
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
@Table("public.\"copernicus_price\"")
public class CopernicusPriceEntity implements Persistable<String> {

  @Id
  private String id;
  private String name;
  private BigInteger price;
  private Instant updatedAt;

  @Transient
  private boolean isNew = true;

  public void setIsNew(boolean isNew) {
    this.isNew = isNew;
  }

  public boolean isNew() {
    return isNew;
  }
}
