package com.ahmadthesis.image.application.port.output;

import com.ahmadthesis.image.domain.payment.Payment;
import reactor.core.publisher.Mono;

public interface PaymentDatabase {
  Mono<Payment> getRecentPayment(String userId);
}
