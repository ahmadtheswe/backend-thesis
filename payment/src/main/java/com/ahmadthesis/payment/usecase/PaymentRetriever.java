package com.ahmadthesis.payment.usecase;

import com.ahmadthesis.payment.business.ActivePackage;
import com.ahmadthesis.payment.business.PackageCount;
import com.ahmadthesis.payment.business.Payment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentRetriever {
  Mono<Payment> getPaymentById(String paymentId);
  Mono<ActivePackage> getActivePackage(String userId);
  Mono<Payment> getOnProgressPaymentByUserId(String userId);
  Flux<PackageCount> getActiveSubscriptionCount();
}
