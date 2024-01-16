package com.ahmadthesis.payment.provider;

import com.ahmadthesis.payment.business.Package;

public class PackageConverter {

  public static Package toBusiness(final PackageEntity entity) {
    return Package.builder()
        .id(entity.getId())
        .packageName(entity.getPackageName())
        .price(entity.getPrice())
        .isActive(entity.getIsActive())
        .build();
  }
}
