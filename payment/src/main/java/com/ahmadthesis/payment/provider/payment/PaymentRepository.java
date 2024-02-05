package com.ahmadthesis.payment.provider.payment;

import java.time.Instant;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentRepository extends R2dbcRepository<PaymentEntity, String> {
  Mono<PaymentEntity> getPaymentEntityById(String id);

  Flux<PaymentEntity> getPaymentEntitiesByUserIdAndPaymentStatusAndValidDateIsAfter(
      String userId, String paymentStatus, Instant today
  );
}
