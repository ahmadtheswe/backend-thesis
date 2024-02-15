package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.application.port.output.PaymentDatabase;
import com.ahmadthesis.image.application.usecase.PaymentService;
import com.ahmadthesis.image.domain.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  private final PaymentDatabase database;

  @Override
  public Mono<Payment> getRecentPayment(String userId) {
    return database.getRecentPayment(userId);
  }
}
