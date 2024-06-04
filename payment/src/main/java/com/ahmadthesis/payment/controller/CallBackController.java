package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.controller.dto.MidtransCallBackDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("payment/callback")
@RequiredArgsConstructor
public class CallBackController {
  private final PersistCallBack persistPayment;
  @PostMapping("")
  public Mono<Boolean> paymentCallBack(@RequestBody final MidtransCallBackDTO midtransCallBackDTO) {
    return persistPayment.paymentCallBack(midtransCallBackDTO);
  }
}
