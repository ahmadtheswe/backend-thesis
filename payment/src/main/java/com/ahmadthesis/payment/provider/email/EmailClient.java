package com.ahmadthesis.payment.provider.email;

import com.ahmadthesis.payment.business.Email;
import reactor.core.publisher.Mono;

public interface EmailClient {
  Mono<Void> sendEmail(Email email);
}
