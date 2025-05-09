package com.ahmadthesis.payment.provider.midtrans;

import com.ahmadthesis.payment.business.Charge;
import com.ahmadthesis.payment.business.Payment;
import com.ahmadthesis.payment.business.PreOrder;
import com.ahmadthesis.payment.business.PreOrderCharge;
import com.ahmadthesis.payment.usecase.MidtransPersister;
import com.ahmadthesis.payment.usecase.MidtransRetriever;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransCoreApi;
import com.midtrans.service.MidtransSnapApi;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MidtransProvider implements MidtransPersister, MidtransRetriever {

  private final MidtransCoreApi midtransCoreApi;
  private final MidtransSnapApi midtransSnapApi;

  @Override
  public Charge paymentCharge(Payment payment) {
    try {
      final String orderId = UUID.randomUUID().toString();
      final Map<String, Object> transactionDetails = new HashMap<>();
      transactionDetails.put("order_id", orderId);
      transactionDetails.put("gross_amount", payment.getPackageType().getValue());
      transactionDetails.put("email", payment.getEmail());

      final Map<String, Object> chargeRequest = new HashMap<>();
      chargeRequest.put("transaction_details", transactionDetails);
      if(payment.getPaymentType() != null)
        chargeRequest.put("payment_type", payment.getPaymentType());

      return Charge.builder()
          .orderId(orderId)
          .redirectUrl(midtransSnapApi.createTransactionRedirectUrl(chargeRequest))
          .midtransToken(midtransSnapApi.createTransactionToken(chargeRequest))
          .build();
    } catch (MidtransError e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public PreOrderCharge preOrderCharge(PreOrder preOrder, BigInteger copernicusPrice) {
    try {
      final String orderId = UUID.randomUUID().toString();
      final Map<String, Object> transactionDetails = new HashMap<>();
      final BigInteger price = BigInteger.valueOf(preOrder.getImageSize().intValue()).multiply(copernicusPrice);
      transactionDetails.put("order_id", orderId);
      transactionDetails.put("gross_amount", price);
      transactionDetails.put("email", preOrder.getUserEmail());

      final Map<String, Object> chargeRequest = new HashMap<>();
      chargeRequest.put("transaction_details", transactionDetails);

      return PreOrderCharge.builder()
          .orderId(orderId)
          .redirectUrl(midtransSnapApi.createTransactionRedirectUrl(chargeRequest))
          .midtransToken(midtransSnapApi.createTransactionToken(chargeRequest))
          .price(price)
          .build();
    } catch (MidtransError e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Mono<Map<String, Object>> checkCharge(String orderId) {
    try {
      Map<String, Object> response = midtransCoreApi.checkTransaction(orderId).toMap();
      return Mono.just(response);
    } catch (MidtransError e) {
      throw new RuntimeException(e);
    }
  }
}
