package com.ahmadthesis.payment.provider.payment;

import com.ahmadthesis.payment.business.Email;
import com.ahmadthesis.payment.business.PaymentCallBack;
import com.ahmadthesis.payment.business.PaymentStatus;
import com.ahmadthesis.payment.provider.email.EmailClient;
import com.ahmadthesis.payment.provider.image.ImageWebClient;
import com.ahmadthesis.payment.provider.image.PreOrderCallBackRequest;
import com.ahmadthesis.payment.provider.preorder.PreOrderEntity;
import com.ahmadthesis.payment.provider.preorder.PreOrderRepository;
import com.ahmadthesis.payment.usecase.CallBackPersister;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@RequiredArgsConstructor
public class CallBackDBProvider implements CallBackPersister {

  @Value("${midtrans.server-key}")
  private String MIDTRANS_SERVER_KEY;

  @Value("${time.zone}")
  private String ZONE_DATE_TIME_ID;

  private final PaymentRepository paymentRepository;
  private final PreOrderRepository preOrderRepository;
  private final ImageWebClient imageWebClient;
  private final EmailClient emailClient;

  @Override
  public Mono<Boolean> updatePaymentStatus(PaymentCallBack paymentCallBack) {
    try {
      final String paymentSHA = Sha512DigestUtils.shaHex(
          paymentCallBack.getOrderId() + paymentCallBack.getStatusCode()
              + paymentCallBack.getGrossAmount() + MIDTRANS_SERVER_KEY);

      if (paymentSHA.equals(paymentCallBack.getSignatureKey())) {
        if (paymentCallBack.getTransactionStatus().equalsIgnoreCase("settlement")) {
          return paymentRepository.getPaymentEntityById(paymentCallBack.getOrderId())
              .flatMap(paymentEntity -> updatePaymentEntity(paymentEntity, paymentCallBack))
              .switchIfEmpty(preOrderRepository.getPreOrderEntityById(paymentCallBack.getOrderId())
                  .flatMap(
                      preOrderEntity -> updatePreOrderEntity(preOrderEntity, paymentCallBack)

                          .flatMap(success -> {
                            return sendEmail(preOrderEntity.getUserEmail(),
                              "Payment Success",
                              "Your payment has been successfully processed.")
                                .then(Mono.defer(() -> Mono.just(success)));
                          })));
        }
      }
      return Mono.just(false);
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

  private Mono<Boolean> updatePaymentEntity(PaymentEntity paymentEntity,
      PaymentCallBack paymentCallBack) {
    paymentEntity.setPaymentStatus(PaymentStatus.PAID.getStatus());
    ZonedDateTime payDate = parseDateTime(paymentCallBack.getTransactionTime());
    paymentEntity.setPayDate(payDate.toInstant());
    paymentEntity.setValidDate(payDate.plusDays(30).toInstant());
    paymentEntity.setIsNew(false);
    return paymentRepository.save(paymentEntity).thenReturn(true);
  }

  private Mono<Boolean> updatePreOrderEntity(PreOrderEntity preOrderEntity,
      PaymentCallBack paymentCallBack) {
    preOrderEntity.setIsPaid(true);
    ZonedDateTime payDate = parseDateTime(paymentCallBack.getTransactionTime());
    preOrderEntity.setPaidAt(payDate.toInstant());
    preOrderEntity.setIsNew(false);
    return preOrderRepository.save(preOrderEntity)
        .thenReturn(true)
        .publishOn(Schedulers.boundedElastic())
        .doOnSuccess(success -> {
          final String preOrderSHA = Sha512DigestUtils.shaHex(
              preOrderEntity.getId() + preOrderEntity.getUserEmail()
                  + preOrderEntity.getUserId() + MIDTRANS_SERVER_KEY);
          imageWebClient.callPreorderImageCallBack(
              PreOrderCallBackRequest.builder()
                  .preorderId(preOrderEntity.getId())
                  .preorderSignature(preOrderSHA)
                  .build()).subscribe();
        });
  }

  private ZonedDateTime parseDateTime(String transactionTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse(transactionTime, formatter);
    return ZonedDateTime.of(localDateTime, ZoneId.of(ZONE_DATE_TIME_ID));
  }

  private Mono<Void> sendEmail(String email, String subject, String content) {
    return emailClient.sendEmail(Email.builder()
        .to(email)
        .subject(subject)
        .content(content)
        .build());
  }
}
