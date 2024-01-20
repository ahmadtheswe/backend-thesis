package com.ahmadthesis.payment.controller;

import com.midtrans.service.MidtransCoreApi;
import com.midtrans.service.MidtransSnapApi;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class MidtransController {
  private final PersistPayment persistPayment;

  @PostMapping("/charge")
  public Mono<Map<String, String>> createCharge(@RequestBody final TransactionsDTO transactionRequest) {
    return persistPayment.saveCharge(transactionRequest);
  }

  @GetMapping("/check")
  public Mono<Object> checkCharge(@RequestParam final String orderId) {
    return persistPayment.checkCharge(orderId);
  }
}
