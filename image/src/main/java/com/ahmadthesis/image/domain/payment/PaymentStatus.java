package com.ahmadthesis.image.domain.payment;

import lombok.Getter;

@Getter
public enum PaymentStatus {
  UNPAID("UNPAID"),
  PAID("PAID"),
  CANCELLED("CANCELLED"),
  FAILED("FAILED");

  private final String status;

  PaymentStatus(final String status) {
    this.status = status;
  }

}
