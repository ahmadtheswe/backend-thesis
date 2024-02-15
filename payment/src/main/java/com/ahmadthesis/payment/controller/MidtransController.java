package com.ahmadthesis.payment.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
  public Mono<Map<String, String>> createCharge(
      @RequestBody final TransactionsDTO transactionRequest,
      final JwtAuthenticationToken auth
      ) {
    String userId = auth.getToken().getClaimAsString(StandardClaimNames.SUB);
    return persistPayment.saveCharge(transactionRequest, userId);
  }

  @GetMapping("/check")
  public Mono<Object> checkCharge(@RequestParam final String orderId) {
    return persistPayment.checkCharge(orderId);
  }

  @GetMapping("")
  public Mono<ActivePackageDTO> getActivePackage(final JwtAuthenticationToken auth) {
    String userId = auth.getToken().getClaimAsString(StandardClaimNames.SUB);
    return persistPayment.getActivePayment(userId);
  }
}
