package com.ahmadthesis.payment.provider.payment;

import com.ahmadthesis.payment.business.Payment;
import com.ahmadthesis.payment.usecase.PaymentPersister;
import com.ahmadthesis.payment.usecase.PaymentRetriever;
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
}
