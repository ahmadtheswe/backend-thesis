package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.business.PackageType;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransCoreApi;
import com.midtrans.service.MidtransSnapApi;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  private final MidtransCoreApi midtransCoreApi;
  private final MidtransSnapApi midtransSnapApi;

  @PostMapping("/charge")
  public Mono<Map<String, String>> createCharge(@RequestBody final TransactionsDTO transactionRequest) {
    return persistPayment.saveCharge(transactionRequest);
  }

//  @GetMapping("/status/{paymentId}")
//  public Mono<PaymentStatusDTO> getPaymentStatus(@PathVariable final String paymentId) {
//
//  }

  @GetMapping("/check")
  public Mono<Object> checkCharge(@RequestParam final String orderId) {
    return persistPayment.checkCharge(orderId);
  }
}
