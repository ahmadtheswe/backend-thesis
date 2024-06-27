package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.business.CopernicusPrice;
import com.ahmadthesis.payment.controller.dto.CopernicusPriceDTO;
import com.ahmadthesis.payment.controller.dto.CopernicusPriceUpdateDTO;
import java.time.ZoneId;

public class CopernicusPriceConverter {
  public static CopernicusPriceDTO toDTO(CopernicusPrice copernicusPrice) {
    return CopernicusPriceDTO.builder()
      .id(copernicusPrice.getId())
      .name(copernicusPrice.getName())
      .price(copernicusPrice.getPrice())
      .updatedAt(copernicusPrice.getUpdatedAt())
      .build();
  }

  public static CopernicusPrice toBusiness(CopernicusPriceDTO copernicusPriceDTO) {
    return CopernicusPrice.builder()
      .id(copernicusPriceDTO.getId())
      .name(copernicusPriceDTO.getName())
      .price(copernicusPriceDTO.getPrice())
      .updatedAt(copernicusPriceDTO.getUpdatedAt())
      .build();
  }

  public static CopernicusPrice toBusiness(CopernicusPriceUpdateDTO copernicusPriceUpdateDTO) {
    return CopernicusPrice.builder()
      .id(copernicusPriceUpdateDTO.getId())
      .price(copernicusPriceUpdateDTO.getPrice())
      .build();
  }
}
