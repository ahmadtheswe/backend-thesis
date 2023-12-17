package com.ahmadthesis.payment.controller;

import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransCoreApi;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
  private final MidtransCoreApi midtransCoreApi;

  @PostMapping("/charge")
  public Mono<Object> createCharge(@RequestBody final TransactionsDTO transactionRequest) {
    try {
      final Map<String, Object> transactionDetails = new HashMap<>();
      transactionDetails.put("order_id", transactionRequest.getOrderId());
      transactionDetails.put("gross_amount", transactionRequest.getGrossAmount());

      final Map<String, Object> chargeRequest = new HashMap<>();
      chargeRequest.put("transaction_details", transactionDetails);
      chargeRequest.put("payment_type", transactionRequest.getPaymentType());

      final Map<String, Object> bank = new HashMap<>();
      bank.put("bank", transactionRequest.getBank());

      chargeRequest.put("bank_transfer", bank);

      final Set<Map<String, Object>> itemDetails = new HashSet<>();
      transactionRequest.getItemDetails().forEach(itemDetailsDTO -> {
        Map<String, Object> item = new HashMap<>();
        item.put("id", itemDetailsDTO.getId());
        item.put("price", itemDetailsDTO.getPrice());
        item.put("quantity", itemDetailsDTO.getQuantity());
        item.put("name", itemDetailsDTO.getName());
        itemDetails.add(item);
      });

      chargeRequest.put("item_details", itemDetails);

      return Mono.just(midtransCoreApi.chargeTransaction(chargeRequest));
    } catch (MidtransError e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping("/check")
  public Mono<Object> checkCharge(@RequestParam final String orderId) {
    try {
      Map<String, Object> response = midtransCoreApi.checkTransaction(orderId).toMap();
      return Mono.just(response);
    } catch (MidtransError e) {
      throw new RuntimeException(e);
    }
  }
}
