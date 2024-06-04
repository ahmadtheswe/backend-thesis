package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.controller.dto.MidtransCallBackDTO;
import reactor.core.publisher.Mono;

public interface PersistCallBack {
  Mono<Boolean> paymentCallBack(MidtransCallBackDTO midtransCallBackDTO);
}
