package com.ahmadthesis.payment.provider.payment;

import com.ahmadthesis.payment.business.ActivePackage;
import com.ahmadthesis.payment.business.PackageType;
import com.ahmadthesis.payment.business.Payment;
import com.ahmadthesis.payment.business.PaymentCallBack;
import com.ahmadthesis.payment.business.PaymentStatus;
import com.ahmadthesis.payment.usecase.PaymentPersister;
import com.ahmadthesis.payment.usecase.PaymentRetriever;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentDBProvider implements PaymentPersister, PaymentRetriever {

  @Value("${midtrans.server-key}")
  private String serverKey;

  @Value("${time.zone}")
  private String zoneDateTime;

  private final PaymentRepository paymentRepository;

  @Override
  public Mono<Void> savePayment(Payment payment, Boolean isNew) {
    final PaymentEntity entity = PaymentConverter.toEntity(payment);
    entity.setIsNew(isNew);
    return paymentRepository.save(entity).then();
  }

  @Override
  public Mono<Payment> getPaymentById(String paymentId) {
    return paymentRepository.getPaymentEntityById(paymentId).map(PaymentConverter::toBusiness);
  }

  @Override
  public Mono<ActivePackage> getActivePackage(String userId) {
    final Instant today = ZonedDateTime.now(ZoneId.of("Asia/Jakarta")).toInstant();
    return paymentRepository.getPaymentEntitiesByUserIdAndPaymentStatusAndValidDateIsAfter(
            userId, PaymentStatus.PAID.getStatus(), today
        )
        // Filter PREMIUM
        .filter(
            paymentEntity -> paymentEntity.getPackageId().contains(PackageType.PREMIUM.getName()))
        .next().flatMap(paymentEntity -> Mono.just(
            ActivePackage.builder().activePackage(PackageType.PREMIUM)
                .activeUntil(paymentEntity.getValidDate().atZone(ZoneId.of(zoneDateTime))).build()))
        .switchIfEmpty(
            paymentRepository.getPaymentEntitiesByUserIdAndPaymentStatusAndValidDateIsAfter(
                    userId, PaymentStatus.PAID.getStatus(), today
                )
                // FILTER PRO
                .filter(paymentEntity -> paymentEntity.getPackageId()
                    .contains(PackageType.PRO.getName()))
                .next().flatMap(paymentEntity -> Mono.just(
                    ActivePackage.builder().activePackage(PackageType.PRO)
                        .activeUntil(paymentEntity.getValidDate().atZone(ZoneId.of(zoneDateTime)))
                        .build()))
                .switchIfEmpty(
                    // Return FREE if not contain PREMIUM or PRO
                    Mono.just(ActivePackage.builder().activePackage(PackageType.FREE).build()))
        );
  }

  @Override
  public Mono<Payment> getOnProgressPaymentByUserId(String userId) {
    final Instant today = ZonedDateTime.now(ZoneId.of("Asia/Jakarta")).toInstant();
    return paymentRepository.getPaymentEntityByUserIdAndPaymentStatusAndPaymentDueDateAfterOrderByPaymentDueDateDesc(
        userId,
        PaymentStatus.UNPAID.getStatus(), today).map(PaymentConverter::toBusiness);
  }

  @Override
  public Mono<Void> deleteActivePayment(String userId) {
    final Instant today = ZonedDateTime.now(ZoneId.of("Asia/Jakarta")).toInstant();
    return paymentRepository.getPaymentEntityByUserIdAndPaymentStatusAndPaymentDueDateAfterOrderByPaymentDueDateDesc(
            userId,
            PaymentStatus.UNPAID.getStatus(), today)
        .flatMap(paymentRepository::delete);
  }

  @Override
  public Mono<Boolean> updatePaymentStatus(PaymentCallBack paymentCallBack) {
    final String paymentSHA = Sha512DigestUtils.shaHex(
        paymentCallBack.getOrderId() + paymentCallBack.getStatusCode()
            + paymentCallBack.getGrossAmount() + serverKey);

    if (paymentSHA.equals(paymentCallBack.getSignatureKey())) {
      return paymentRepository.getPaymentEntityById(paymentCallBack.getOrderId())
          .flatMap(paymentEntity -> {
            paymentEntity.setPaymentStatus(PaymentStatus.PAID.getStatus());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(
                paymentCallBack.getTransactionTime(), formatter);
            ZonedDateTime payDate = ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Jakarta"));

            System.out.println("Payment time: " + payDate);
            paymentEntity.setPayDate(payDate.toInstant());
            paymentEntity.setValidDate(payDate.plusDays(30).toInstant());
            paymentEntity.setIsNew(false);
            return paymentRepository.save(paymentEntity).then(Mono.defer(() -> Mono.just(true)));
          });
    }

    return Mono.just(false);
  }
}
