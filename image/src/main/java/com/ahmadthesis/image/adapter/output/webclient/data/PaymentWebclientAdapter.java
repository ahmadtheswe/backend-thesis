package com.ahmadthesis.image.adapter.output.webclient.data;

import com.ahmadthesis.image.application.port.output.PaymentWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentWebclientAdapter implements PaymentWebClient {

  @Value("${api.payment.get-subscription-level}")
  private String paymentApi;

  @Override
  public Mono<String> getActivePackage(final String token) {
    final WebClient webClient = WebClient.builder()
        .baseUrl(paymentApi)
        .defaultHeader("Authorization", "Bearer " + token)
        .build();
    return webClient.get()
        .retrieve()
        .bodyToMono(ActivePackageDTO.class)
        .map(ActivePackageDTO::getActivePackage);
  }
}
