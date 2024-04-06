package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.business.ActivePackage;
import com.ahmadthesis.payment.controller.dto.ActivePackageDTO;

public class ActivePackageConverter {
  public static ActivePackageDTO toDTO(final ActivePackage activePackage) {
    return ActivePackageDTO.builder()
        .activePackage(activePackage.getActivePackage().getName())
        .activeUntil(activePackage.getActiveUntil())
        .build();
  }
}
