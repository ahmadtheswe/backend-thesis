package com.ahmadthesis.image.application.usecase;

import com.ahmadthesis.image.domain.image.BBox;
import reactor.core.publisher.Mono;

public interface CopernicusWebClientService {
  Mono<byte[]> generateImage(BBox bBox, String probeType);
}
