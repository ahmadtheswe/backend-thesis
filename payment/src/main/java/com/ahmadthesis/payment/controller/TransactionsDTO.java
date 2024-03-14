package com.ahmadthesis.payment.controller;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionsDTO {
  private String email;
  private String paymentType;
  private String bank;
  private String packageType;
  private Set<ItemDetailsDTO> itemDetails;
}
