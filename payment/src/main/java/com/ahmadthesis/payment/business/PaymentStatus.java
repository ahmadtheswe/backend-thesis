package com.ahmadthesis.payment.business;

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
