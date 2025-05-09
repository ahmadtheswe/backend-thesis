package com.ahmadthesis.payment.usecase.payment;

import com.ahmadthesis.payment.business.PackageType;
import com.ahmadthesis.payment.business.Payment;
import com.ahmadthesis.payment.business.PaymentStatus;
import com.ahmadthesis.payment.controller.ActivePackageConverter;
import com.ahmadthesis.payment.controller.PaymentCallBackConverter;
import com.ahmadthesis.payment.controller.dto.ActivePackageDTO;
import com.ahmadthesis.payment.controller.ChargeConverter;
import com.ahmadthesis.payment.controller.dto.ChargeDTO;
import com.ahmadthesis.payment.controller.PaymentConverter;
import com.ahmadthesis.payment.controller.dto.MidtransCallBackDTO;
import com.ahmadthesis.payment.controller.dto.PackageCountDTO;
import com.ahmadthesis.payment.controller.dto.PaymentDTO;
import com.ahmadthesis.payment.controller.PersistPayment;
import com.ahmadthesis.payment.controller.dto.PaymentTransactionsDTO;
import com.ahmadthesis.payment.usecase.MidtransPersister;
import com.ahmadthesis.payment.usecase.MidtransRetriever;
import com.ahmadthesis.payment.usecase.PaymentPersister;
import com.ahmadthesis.payment.usecase.PaymentRetriever;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentUseCaseApi implements PersistPayment {

  private final MidtransPersister midtransPersister;
  private final MidtransRetriever midtransRetriever;
  private final PaymentPersister paymentPersister;
  private final PaymentRetriever paymentRetriever;

  @Override
  public Mono<ChargeDTO> saveCharge(final PaymentTransactionsDTO paymentTransactionsDTO, final String userId) {
    return Mono.fromSupplier(() -> midtransPersister.paymentCharge(
            PaymentConverter.toPayment(paymentTransactionsDTO)))
        .flatMap(charge -> {
          final Payment payment = Payment.builder()
              .id(charge.getOrderId())
              .email(paymentTransactionsDTO.getEmail())
              .userId(userId)
              .packageType(PackageType.valueOf(paymentTransactionsDTO.getPackageType()))
              .paymentType(paymentTransactionsDTO.getPaymentType())
              .paymentStatus(PaymentStatus.UNPAID)
              .redirectUrl(charge.getRedirectUrl())
              .midtransToken(charge.getMidtransToken())
              .build();

          return paymentPersister.savePayment(payment, true).thenReturn(charge);
        })
        .map(ChargeConverter::toDTO);
  }

  @Override
  public Mono<Object> checkCharge(final String orderId) {
    return midtransRetriever.checkCharge(orderId)
        .flatMap(midtransResp -> paymentRetriever.getPaymentById(orderId).flatMap(payment -> {
          if (midtransResp.get("transaction_status").equals("settlement")) {
            payment.setPaymentStatus(PaymentStatus.PAID);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(
                midtransResp.get("transaction_time").toString(), formatter);
            ZonedDateTime payDate = ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Jakarta"));

            System.out.println("Payment time: " + payDate);
            payment.setPayDate(payDate);
            payment.setValidDate(payDate.plusDays(30));

            return paymentPersister.savePayment(payment, false)
                .then(Mono.defer(() -> Mono.just(payment)));
          }

          return Mono.error(
              new ResponseStatusException(HttpStatus.NOT_FOUND, "On progress payment not found"));
        }));
  }

  @Override
  public Mono<ActivePackageDTO> getActivePayment(final String userId) {
    return paymentRetriever.getActivePackage(userId).map(ActivePackageConverter::toDTO);
  }

  @Override
  public Mono<PaymentDTO> getOnProgressPayment(final String userId) {
    return paymentRetriever.getOnProgressPaymentByUserId(userId)
        .map(PaymentConverter::toPaymentDTO);
  }

  @Override
  public Mono<Void> cancelActivePayment(final String userId) {
    return paymentPersister.deleteActivePayment(userId);
  }

  @Override
  public Flux<PackageCountDTO> getPackageSubscriptionCount() {
    return paymentRetriever.getActiveSubscriptionCount()
        .map(packageCount -> PackageCountDTO.builder()
            .packageName(packageCount.getPackageName())
            .subscriptionTotal(packageCount.getSubscriptionTotal())
            .build());
  }
}
