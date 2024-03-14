package com.ahmadthesis.payment.provider.payment;

import com.ahmadthesis.payment.business.ActivePackage;
import com.ahmadthesis.payment.business.PackageType;
import com.ahmadthesis.payment.business.Payment;
import com.ahmadthesis.payment.business.PaymentStatus;
import com.ahmadthesis.payment.usecase.PaymentPersister;
import com.ahmadthesis.payment.usecase.PaymentRetriever;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentDBProvider implements PaymentPersister, PaymentRetriever {

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
    ).flatMap(paymentEntity -> {
      if (paymentEntity.getPackageId().contains(PackageType.PREMIUM.getName())) {
        return Mono.just(ActivePackage.builder().activePackage(PackageType.PREMIUM).build());
      } else if (paymentEntity.getPackageId().contains(PackageType.PRO.getName())) {
        return Mono.just(ActivePackage.builder().activePackage(PackageType.PRO).build());
      } else {
        return Mono.just(ActivePackage.builder().activePackage(PackageType.FREE).build());
      }
    }).next().switchIfEmpty(Mono.defer(
        () -> Mono.just(ActivePackage.builder().activePackage(PackageType.FREE).build())));
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
}
