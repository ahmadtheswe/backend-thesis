package com.ahmadthesis.image.adapter.output.webclient.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChargeDTO {
  private String redirectUrl;
  private String orderId;
  private String midtransToken;
}
