package com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.preorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreOrderCallBackRequest {

  @JsonProperty("preorderId")
  private String preorderId;

  @JsonProperty("preorderSignature")
  private String preorderSignature;
}
