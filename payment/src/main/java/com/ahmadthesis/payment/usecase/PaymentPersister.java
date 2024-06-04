package com.ahmadthesis.payment.usecase;

import com.ahmadthesis.payment.business.Payment;
import com.ahmadthesis.payment.business.PaymentCallBack;
import reactor.core.publisher.Mono;

public interface PaymentPersister {
  Mono<Void> savePayment(Payment payment, Boolean isNew);
  Mono<Void> deleteActivePayment(String userId);
}
