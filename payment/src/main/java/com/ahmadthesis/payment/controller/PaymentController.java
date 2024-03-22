package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.controller.dto.ActivePackageDTO;
import com.ahmadthesis.payment.controller.dto.ChargeDTO;
import com.ahmadthesis.payment.controller.dto.PaymentDTO;
import com.ahmadthesis.payment.controller.dto.TransactionsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class PaymentController {

  private final PersistPayment persistPayment;

  @PostMapping("/charge")
  public Mono<ChargeDTO> createCharge(
      @RequestBody final TransactionsDTO transactionRequest,
      final JwtAuthenticationToken auth
  ) {
    String userId = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);
    return persistPayment.saveCharge(transactionRequest, userId);
  }

  @GetMapping("/check")
  public Mono<Object> checkCharge(@RequestParam final String orderId) {
    return persistPayment.checkCharge(orderId);
  }

  @PostMapping("/callback")
  public Mono<Object> paymentCallBack(@RequestParam final String orderId) {
    return persistPayment.checkCharge(orderId);
  }

  @GetMapping("")
  public Mono<ActivePackageDTO> getActivePackage(final JwtAuthenticationToken auth) {
    final String userId = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);
    return persistPayment.getActivePayment(userId);
  }

  @GetMapping("/on-progress")
  public Mono<PaymentDTO> getOnProgressPayment(final JwtAuthenticationToken auth) {
    final String userId = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);
    return persistPayment.getOnProgressPayment(userId)
        .switchIfEmpty(Mono.error(
            new ResponseStatusException(HttpStatus.NOT_FOUND, "On progress payment not found")));
  }

  @DeleteMapping("/cancel")
  public Mono<Void> cancelActivePayment(final JwtAuthenticationToken auth) {
    final String userId = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);
    return persistPayment.cancelActivePayment(userId);
  }
}
