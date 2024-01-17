package com.ahmadthesis.payment.usecase;

import com.ahmadthesis.payment.business.Payment;
import reactor.core.publisher.Mono;

public interface PaymentRetriever {
  Mono<Payment> getPaymentById(String paymentId);
}
