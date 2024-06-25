package com.ahmadthesis.image.application.port.output;

import com.ahmadthesis.image.adapter.output.webclient.data.CopernicusTokenDTO;
import com.ahmadthesis.image.domain.image.BBox;
import reactor.core.publisher.Mono;

public interface CopernicusWebClient {
  Mono<CopernicusTokenDTO> generateCopernicusToken();
  Mono<byte[]> getCopernicusImage(String token, BBox bBox, String probeType);
}
