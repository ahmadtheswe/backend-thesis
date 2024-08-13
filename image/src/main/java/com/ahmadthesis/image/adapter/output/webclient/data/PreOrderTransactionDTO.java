package com.ahmadthesis.image.adapter.output.webclient.data;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PreOrderTransactionDTO {
  private String email;
  private Double imageSize;
  private String probeType;
}
