package com.ahmadthesis.payment.controller.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ItemDetailsDTO {
  private String id;
  private String name;
  private Integer price;
  private Integer quantity;
}
