package com.ahmadthesis.payment.provider.preorder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Generated
@Table("public.\"preorder\"")
public class PreOrderEntity implements Persistable<String> {
  @Id
  private String id;
  private String userId;
  private String userEmail;
  private BigInteger price;
  private Boolean isPaid;
  private Instant createdAt;
  private Instant paidAt;
  private String redirectUrl;
  private String midtransToken;
  private Instant paymentDueDate;

  @Transient
  private boolean isNew = true;

  public void setIsNew(boolean isNew) { this.isNew = isNew; }
  public boolean isNew() { return isNew; }

}
