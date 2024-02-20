package com.justahmed99.authapp.provider.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentWebClientProvider implements PaymentWebClientRetriever{

  @Value("${api.payment.get-subscription-level}")
  private String paymentApi;

  @Override
  public Mono<String> getActivePackage(String token) {
    final WebClient webClient = WebClient.builder()
        .baseUrl(paymentApi)
        .defaultHeader("Authorization", "Bearer " + token)
        .build();
    return webClient.get()
        .retrieve()
        .bodyToMono(ActivePackage.class)
        .map(ActivePackage::getActivePackage);
  }
}
