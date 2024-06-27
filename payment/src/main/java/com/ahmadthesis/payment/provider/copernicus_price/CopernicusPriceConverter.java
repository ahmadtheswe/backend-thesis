package com.ahmadthesis.payment.provider.copernicus_price;

import com.ahmadthesis.payment.business.CopernicusPrice;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CopernicusPriceConverter {

  public static CopernicusPriceEntity toEntity(CopernicusPrice copernicusPrice, Boolean isNew) {
    return CopernicusPriceEntity.builder()
        .id(copernicusPrice.getId())
        .name(copernicusPrice.getName())
        .price(copernicusPrice.getPrice())
        .updatedAt(ZonedDateTime.now(ZoneId.of("Asia/Jakarta")).toInstant())
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
