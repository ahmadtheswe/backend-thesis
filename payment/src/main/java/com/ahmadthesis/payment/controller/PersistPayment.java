package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.controller.dto.ActivePackageDTO;
import com.ahmadthesis.payment.controller.dto.ChargeDTO;
import com.ahmadthesis.payment.controller.dto.MidtransCallBackDTO;
import com.ahmadthesis.payment.controller.dto.PackageCountDTO;
import com.ahmadthesis.payment.controller.dto.PaymentDTO;
import com.ahmadthesis.payment.controller.dto.PaymentTransactionsDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersistPayment {
  Mono<ChargeDTO> saveCharge(PaymentTransactionsDTO paymentTransactionsDTO, String userId);
  Mono<Object> checkCharge(String orderId);
  Mono<ActivePackageDTO> getActivePayment(String userId);
  Mono<PaymentDTO> getOnProgressPayment(String userId);
  Mono<Void> cancelActivePayment(String userId);
  Flux<PackageCountDTO> getPackageSubscriptionCount();
}
