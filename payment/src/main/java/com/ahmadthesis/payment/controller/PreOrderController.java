package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.controller.dto.ChargeDTO;
import com.ahmadthesis.payment.controller.dto.PreOrderTransactionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("payment/pre-order")
@RequiredArgsConstructor
public class PreOrderController {

  private final PersistPreOrder persistPreOrder;

  @PostMapping("/charge")
  public Mono<ChargeDTO> createCharge(
      @RequestBody final PreOrderTransactionDTO transactionRequest,
      final JwtAuthenticationToken auth
  ) {
    String userId = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);
    return persistPreOrder.saveCharge(transactionRequest, userId);
  }
}
