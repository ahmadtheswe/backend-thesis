package com.ahmadthesis.image.application.usecase;

import com.ahmadthesis.image.domain.payment.Payment;
import reactor.core.publisher.Mono;

public interface PaymentService {
  Mono<Payment> getRecentPayment(String userId);
}
