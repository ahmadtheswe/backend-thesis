package com.ahmadthesis.payment.provider.image;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ImageWebClientProvider implements ImageWebClient {

  @Value("${api.image.preorder-callback}")
  private String imagePreorderCallBackUrl;

  @Override
  public Mono<Boolean> callPreorderImageCallBack(PreOrderCallBackRequest preOrderCallBackRequest) {
    final WebClient webClient = WebClient.builder()
        .baseUrl(imagePreorderCallBackUrl)
        .build();
    return webClient.post()
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(preOrderCallBackRequest)
        .retrieve()
        .onStatus(HttpStatusCode::isError,
            clientResponse -> Mono.error(new RuntimeException("Error calling API")))
        .bodyToMono(Void.class)
        .thenReturn(true)
        .onErrorReturn(false);
  }
}
