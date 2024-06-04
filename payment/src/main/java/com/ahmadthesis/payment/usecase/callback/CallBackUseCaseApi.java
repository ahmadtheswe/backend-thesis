package com.ahmadthesis.payment.usecase.callback;

import com.ahmadthesis.payment.controller.PaymentCallBackConverter;
import com.ahmadthesis.payment.controller.PersistCallBack;
import com.ahmadthesis.payment.controller.dto.MidtransCallBackDTO;
import com.ahmadthesis.payment.usecase.CallBackPersister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CallBackUseCaseApi implements PersistCallBack {

  private final CallBackPersister callBackPersister;

  @Override
  public Mono<Boolean> paymentCallBack(MidtransCallBackDTO midtransCallBackDTO) {
    return callBackPersister.updatePaymentStatus(
        PaymentCallBackConverter.toBusiness(midtransCallBackDTO));
  }
}
