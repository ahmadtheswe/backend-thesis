package com.ahmadthesis.payment.provider.copernicus_price;

import com.ahmadthesis.payment.business.CopernicusPrice;
import java.time.ZoneId;

public class CopernicusPriceConverter {

  public static CopernicusPriceEntity toEntity(CopernicusPrice copernicusPrice, Boolean isNew) {
    return CopernicusPriceEntity.builder()
        .id(copernicusPrice.getId())
        .name(copernicusPrice.getName())
        .price(copernicusPrice.getPrice())
        .updatedAt(copernicusPrice.getUpdatedAt() == null ? null
            : copernicusPrice.getUpdatedAt().toInstant())
        .isNew(isNew)
        .build();
  }

  public static CopernicusPrice toBusiness(CopernicusPriceEntity entity) {
    return CopernicusPrice.builder()
        .id(entity.getId())
        .name(entity.getName())
        .price(entity.getPrice())
        .updatedAt(entity.getUpdatedAt() == null ? null
            : entity.getUpdatedAt().atZone(ZoneId.of("Asia/Jakarta")))
        .build();
  }
}
