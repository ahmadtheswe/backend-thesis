package com.ahmadthesis.payment.provider.payment;

import com.ahmadthesis.payment.business.ActivePackage;
import com.ahmadthesis.payment.business.PackageCount;
import com.ahmadthesis.payment.business.PackageType;
import com.ahmadthesis.payment.business.Payment;
import com.ahmadthesis.payment.business.PaymentStatus;
import com.ahmadthesis.payment.provider.preorder.PreOrderRepository;
import com.ahmadthesis.payment.usecase.PaymentPersister;
import com.ahmadthesis.payment.usecase.PaymentRetriever;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentDBProvider implements PaymentPersister, PaymentRetriever {

  @Value("${midtrans.server-key}")
  private String MIDTRANS_SERVER_KEY;

  @Value("${time.zone}")
  private String ZONE_DATE_TIME_ID;

  private final PaymentRepository paymentRepository;
  private final PreOrderRepository preOrderRepository;

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
                .activeUntil(paymentEntity.getValidDate().atZone(ZoneId.of(ZONE_DATE_TIME_ID)))
                .build()))
        .switchIfEmpty(
            paymentRepository.getPaymentEntitiesByUserIdAndPaymentStatusAndValidDateIsAfter(
                    userId, PaymentStatus.PAID.getStatus(), today
                )
                // FILTER PRO
                .filter(paymentEntity -> paymentEntity.getPackageId()
                    .contains(PackageType.PRO.getName()))
                .next().flatMap(paymentEntity -> Mono.just(
                    ActivePackage.builder().activePackage(PackageType.PRO)
                        .activeUntil(paymentEntity.getValidDate().atZone(ZoneId.of(
                            ZONE_DATE_TIME_ID)))
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
  public Flux<PackageCount> getActiveSubscriptionCount() {
    final Instant today = ZonedDateTime.now(ZoneId.of("Asia/Jakarta")).toInstant();
    return paymentRepository.getLatestPaidPaymentSummaryForPackages(today)
        .map(paymentPackageSummaryEntity -> PackageCount.builder()
            .packageName(paymentPackageSummaryEntity.getPackageId())
            .subscriptionTotal(paymentPackageSummaryEntity.getUserCount())
            .build());
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
