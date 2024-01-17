package com.ahmadthesis.payment.provider.payment;

import java.time.Instant;
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
@Table("public.\"payment\"")
public class PaymentEntity implements Persistable<String> {
  @Id
  private String id;
  private String userId;
  private String packageId;
  private String paymentStatus;
  private Instant payDate;
  private Instant validDate;
  @Override
  public boolean isNew() {
    return true;
  }
}
