package com.ahmadthesis.payment.business;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class PreOrderCharge extends Charge {
  private Double price;
}
