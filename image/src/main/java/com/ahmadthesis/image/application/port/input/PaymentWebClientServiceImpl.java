package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.adapter.output.webclient.data.PaymentWebclientAdapter;
import com.ahmadthesis.image.application.usecase.PaymentWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentWebClientServiceImpl implements PaymentWebClientService {

  private final PaymentWebclientAdapter paymentWebclientAdapter;

  @Override
  public Mono<String> getActivePackage(final String token) {
    return paymentWebclientAdapter.getActivePackage(token);
  }
}
