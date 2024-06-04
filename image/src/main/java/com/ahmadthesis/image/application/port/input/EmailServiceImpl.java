package com.ahmadthesis.image.application.port.input;

import com.ahmadthesis.image.application.port.output.EmailPersister;
import com.ahmadthesis.image.application.usecase.EmailService;
import com.ahmadthesis.image.domain.image.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
  private final EmailPersister emailPersister;

  @Override
  public Mono<Void> sendEmail(Email email) {
    return emailPersister.sendEmail(email);
  }
}
