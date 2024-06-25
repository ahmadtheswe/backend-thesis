package com.ahmadthesis.payment.provider.email;

import com.ahmadthesis.payment.business.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EmailClientProvider implements EmailClient {

  private final JavaMailSender mailSender;
  @Value("${email.sender-address}")
  private String senderEmailAddress;

  @Override
  public Mono<Void> sendEmail(final Email email) {
    final SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(senderEmailAddress);
    message.setTo(email.getTo());
    message.setSubject(email.getSubject());
    message.setText(email.getContent());

    return Mono.fromRunnable(() -> mailSender.send(message)).then();
  }
}
