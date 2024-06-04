package com.ahmadthesis.payment.provider.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PreOrderCallBackRequest {

  @JsonProperty("preorderId")
  private String preorderId;

  @JsonProperty("preorderSignature")
  private String preorderSignature;
}
