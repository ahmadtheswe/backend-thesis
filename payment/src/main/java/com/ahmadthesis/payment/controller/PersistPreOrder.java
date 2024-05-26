package com.ahmadthesis.payment.controller;

import com.ahmadthesis.payment.controller.dto.ChargeDTO;
import com.ahmadthesis.payment.controller.dto.PaymentTransactionsDTO;
import com.ahmadthesis.payment.controller.dto.PreOrderTransactionDTO;
import reactor.core.publisher.Mono;

public interface PersistPreOrder {
  Mono<ChargeDTO> saveCharge(PreOrderTransactionDTO preOrderTransactionDTO, String userId);
}
