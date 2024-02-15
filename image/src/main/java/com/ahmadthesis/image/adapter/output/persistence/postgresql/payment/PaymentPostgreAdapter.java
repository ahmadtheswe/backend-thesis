package com.ahmadthesis.image.adapter.output.persistence.postgresql.payment;

import com.ahmadthesis.image.adapter.output.persistence.postgresql.payment.converter.PaymentConverter;
import com.ahmadthesis.image.adapter.output.persistence.postgresql.payment.sql.PaymentRepository;
import com.ahmadthesis.image.application.port.output.PaymentDatabase;
import com.ahmadthesis.image.domain.payment.Payment;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentPostgreAdapter implements PaymentDatabase {

  private final PaymentRepository repository;

  @Override
  public Mono<Payment> getRecentPayment(String userId) {
    final Instant today = ZonedDateTime.now(ZoneId.of("Asia/Jakarta")).toInstant();
    return repository.getFirstByUserIdAndValidDateAfterOrderByValidDateDesc(userId, today)
        .map(PaymentConverter::toBusiness);
  }
}
