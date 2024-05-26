package com.ahmadthesis.payment.controller.dto;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentTransactionsDTO {
  private String email;
  private String paymentType;
  private String bank;
  private String packageType;
  private Set<ItemDetailsDTO> itemDetails;
}
