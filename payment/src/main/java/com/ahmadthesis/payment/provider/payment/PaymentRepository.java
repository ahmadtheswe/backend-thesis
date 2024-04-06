package com.ahmadthesis.payment.provider.payment;

import java.time.Instant;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentRepository extends R2dbcRepository<PaymentEntity, String> {
  Mono<PaymentEntity> getPaymentEntityById(String id);

  Flux<PaymentEntity> getPaymentEntitiesByUserIdAndPaymentStatusAndValidDateIsAfter(
      String userId, String paymentStatus, Instant today
  );

  Mono<PaymentEntity> getPaymentEntityByUserIdAndPaymentStatusAndPaymentDueDateAfterOrderByPaymentDueDateDesc(
      String userId, String paymentStatus, Instant today
  );

  @Query("SELECT package_id, COUNT(DISTINCT user_id) AS user_count FROM payment WHERE payment_status = 'PAID' AND valid_date < :today GROUP BY package_id")
  Flux<PaymentPackageSummaryEntity> getLatestPaidPaymentSummaryForPackages(@Param("today") Instant today);

}
