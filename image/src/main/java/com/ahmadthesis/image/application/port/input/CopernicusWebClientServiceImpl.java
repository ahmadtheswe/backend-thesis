package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.adapter.output.webclient.data.CopernicusWebClientAdapter;
import com.ahmadthesis.image.application.usecase.CopernicusWebClientService;
import com.ahmadthesis.image.domain.image.BBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CopernicusWebClientServiceImpl implements CopernicusWebClientService {

  private final CopernicusWebClientAdapter copernicusWebClientAdapter;

  @Override
  public Mono<byte[]> generateImage(BBox bBox, String probeType) {
    return copernicusWebClientAdapter.generateCopernicusToken()
        .flatMap(copernicusTokenDTO -> {
          return copernicusWebClientAdapter.getCopernicusImage(copernicusTokenDTO.getAccessToken(), bBox, probeType);
        });
  }
}
