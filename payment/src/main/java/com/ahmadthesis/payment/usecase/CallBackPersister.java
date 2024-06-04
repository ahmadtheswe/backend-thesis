package com.ahmadthesis.payment.usecase;

import com.ahmadthesis.payment.business.PaymentCallBack;
import reactor.core.publisher.Mono;

public interface CallBackPersister {
  Mono<Boolean> updatePaymentStatus(PaymentCallBack paymentCallBack);
}
