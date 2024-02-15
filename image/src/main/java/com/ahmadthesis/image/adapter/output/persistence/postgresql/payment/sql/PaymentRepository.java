package com.ahmadthesis.image.adapter.output.persistence.postgresql.payment.sql;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.payment.data.PaymentEntity;
import java.time.Instant;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentRepository extends R2dbcRepository<PaymentEntity, String> {
  Mono<PaymentEntity> getPaymentEntityById(String id);

  Flux<PaymentEntity> getPaymentEntitiesByUserIdAndPaymentStatusAndValidDateIsAfter(
      String userId, String paymentStatus, Instant today
  );

  Mono<PaymentEntity> getFirstByUserIdAndValidDateAfterOrderByValidDateDesc(String userId, Instant today);
}
