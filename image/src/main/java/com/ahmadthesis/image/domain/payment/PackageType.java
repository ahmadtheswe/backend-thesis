package com.ahmadthesis.image.domain.payment;

import lombok.Getter;

@Getter
public enum PackageType {
  FREE("FREE", 0),
  PRO("PRO", 70000),
  PREMIUM("PREMIUM", 100000);

  private final String name;
  private final Integer value;

  PackageType(String name, Integer value) {
    this.name = name;
    this.value = value;
  }
}
