package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.controller.dto.ActivePackageDTO;
import com.ahmadthesis.payment.controller.dto.ChargeDTO;
import com.ahmadthesis.payment.controller.dto.MidtransCallBackDTO;
import com.ahmadthesis.payment.controller.dto.PaymentDTO;
import com.ahmadthesis.payment.controller.dto.TransactionsDTO;
import reactor.core.publisher.Mono;

public interface PersistPayment {
  Mono<ChargeDTO> saveCharge(TransactionsDTO transactionsDTO, String userId);
  Mono<Object> checkCharge(String orderId);
  Mono<Boolean> paymentCallBack(MidtransCallBackDTO midtransCallBackDTO);
  Mono<ActivePackageDTO> getActivePayment(String userId);
  Mono<PaymentDTO> getOnProgressPayment(String userId);
  Mono<Void> cancelActivePayment(String userId);
}
