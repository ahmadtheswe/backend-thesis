package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.business.ActivePackage;

public class ActivePackageConverter {
  public static ActivePackageDTO toDTO(final ActivePackage activePackage) {
    return ActivePackageDTO.builder()
        .activePackage(activePackage.getActivePackage().getName())
        .build();
  }
}
