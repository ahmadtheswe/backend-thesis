package com.ahmadthesis.payment.business;

public enum PaymentStatus {
  PAID("PAID"),
  CANCELLED("CANCELLED"),
  FAILED("FAILED");

  private final String status;

  PaymentStatus(final String status) {
    this.status = status;
  }

  public String getStatus() {
    return this.status;
  }
}
