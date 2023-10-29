package com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CartDeleteRequest {
  private String cartId;
}
