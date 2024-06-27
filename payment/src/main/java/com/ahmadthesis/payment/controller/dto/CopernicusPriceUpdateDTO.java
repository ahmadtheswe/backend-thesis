package com.ahmadthesis.payment.controller.dto;

import java.math.BigInteger;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CopernicusPriceUpdateDTO {
  private String id;
  private BigInteger price;
}
