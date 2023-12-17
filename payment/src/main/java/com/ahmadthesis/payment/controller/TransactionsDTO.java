package com.ahmadthesis.payment.controller;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionsDTO {
  private String orderId;
  private Integer grossAmount;
  private String paymentType;
  private String bank;
  private Set<ItemDetailsDTO> itemDetails;
}
