package com.ahmadthesis.image.application.port.output;

import com.ahmadthesis.image.adapter.output.webclient.data.ChargeDTO;
import com.ahmadthesis.image.adapter.output.webclient.data.PreOrderTransactionDTO;
import reactor.core.publisher.Mono;

public interface PaymentWebClient {
  Mono<String> getActivePackage(String token);
  Mono<ChargeDTO> chargePreOrder(PreOrderTransactionDTO preOrderTransactionDTO, String token);

}
