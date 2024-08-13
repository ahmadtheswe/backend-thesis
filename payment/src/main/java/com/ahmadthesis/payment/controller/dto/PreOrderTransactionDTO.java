package com.ahmadthesis.payment.controller.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PreOrderTransactionDTO {
  private String email;
  private Double imageSize;
  private String probeType;
}
