package com.ahmadthesis.payment.controller.dto;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PreOrderTransactionDTO {
  private String email;
  private Double imageSize;
}
