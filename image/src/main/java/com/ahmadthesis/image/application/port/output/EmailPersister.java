package com.ahmadthesis.image.application.port.output;

import com.ahmadthesis.image.domain.image.Email;
import reactor.core.publisher.Mono;

public interface EmailPersister {
  Mono<Void> sendEmail(Email email);
}
