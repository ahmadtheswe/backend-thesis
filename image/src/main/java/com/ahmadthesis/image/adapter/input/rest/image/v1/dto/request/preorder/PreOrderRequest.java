package com.ahmadthesis.image.adapter.input.rest.image.v1.dto.request.preorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreOrderRequest {
  @JsonProperty("bBox")
  private BBoxRequest bBox;
  @JsonProperty("imageSize")
  private Double imageSize;
  @JsonProperty("probeType")
  private String probeType;
}
