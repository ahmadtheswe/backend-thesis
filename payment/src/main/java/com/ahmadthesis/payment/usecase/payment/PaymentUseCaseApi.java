package com.ahmadthesis.payment.usecase.payment;

import com.ahmadthesis.payment.business.PackageType;
import com.ahmadthesis.payment.business.Payment;
import com.ahmadthesis.payment.business.PaymentStatus;
import com.ahmadthesis.payment.controller.PersistPayment;
import com.ahmadthesis.payment.controller.TransactionConverter;
import com.ahmadthesis.payment.controller.TransactionsDTO;
import com.ahmadthesis.payment.usecase.MidtransPersister;
import com.ahmadthesis.payment.usecase.MidtransRetriever;
import com.ahmadthesis.payment.usecase.PaymentPersister;
import com.ahmadthesis.payment.usecase.PaymentRetriever;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentUseCaseApi implements PersistPayment {

  private final MidtransPersister midtransPersister;
  private final MidtransRetriever midtransRetriever;
  private final PaymentPersister paymentPersister;
  private final PaymentRetriever paymentRetriever;

  @Override
  public Mono<Map<String, String>> saveCharge(TransactionsDTO transactionsDTO) {
    return Mono.fromSupplier(() -> midtransPersister.charge(
            TransactionConverter.toPayment(transactionsDTO)))
        .flatMap(charge -> {
          final Payment payment = Payment.builder()
              .id(charge.get("orderId"))
              .email(transactionsDTO.getEmail())
              .userId(transactionsDTO.getUserId())
              .packageType(PackageType.valueOf(transactionsDTO.getPackageType()))
              .paymentType(transactionsDTO.getPaymentType())
              .paymentStatus(PaymentStatus.UNPAID)
              .build();

          return paymentPersister.savePayment(payment, true).thenReturn(charge);
        });
  }

  @Override
  public Mono<Object> checkCharge(String orderId) {
    return midtransRetriever.checkCharge(orderId)
        .flatMap(midtransResp -> {
          return paymentRetriever.getPaymentById(orderId).flatMap(payment -> {
            if (midtransResp.get("transaction_status").equals("settlement")) {
              payment.setPaymentStatus(PaymentStatus.PAID);

              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
              LocalDateTime localDateTime = LocalDateTime.parse(
                  midtransResp.get("transaction_time").toString(), formatter);
              ZonedDateTime payDate = ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Jakarta"));

              System.out.println("Payment time: " + payDate);
              payment.setPayDate(payDate);
              payment.setValidDate(payDate.plusDays(30));

              return paymentPersister.savePayment(payment, false).then();
            }
            return Mono.just(payment);
          });
        });
  }
}
