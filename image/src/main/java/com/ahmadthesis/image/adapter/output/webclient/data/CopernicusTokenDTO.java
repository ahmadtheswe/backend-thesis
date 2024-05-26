package com.ahmadthesis.image.adapter.output.webclient.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CopernicusTokenDTO {
  @JsonProperty("access_token")
  private String accessToken;
  private Integer expiresIn;
  private Integer refreshExpiresIn;
  private String tokenTye;

}
