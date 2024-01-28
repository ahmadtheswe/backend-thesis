package com.justahmed99.authapp.provider.email;

import reactor.core.publisher.Mono;

public interface EmailPersister {
  Mono<Void> sendEmail(Email email);
}
