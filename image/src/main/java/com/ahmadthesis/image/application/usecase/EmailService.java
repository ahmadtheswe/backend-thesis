package com.ahmadthesis.image.application.usecase;

import com.ahmadthesis.image.domain.image.Email;
import reactor.core.publisher.Mono;

public interface EmailService {
  Mono<Void> sendEmail(Email email);
}
