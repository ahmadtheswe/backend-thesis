package com.ahmadthesis.auth.keycloak_client.usecase;

import com.ahmadthesis.auth.keycloak_client.dao.LoginDao;
import com.ahmadthesis.auth.keycloak_client.dao.LoginResponse;
import reactor.core.publisher.Mono;

public interface WebClientUseCase {
  Mono<LoginResponse> login(final LoginDao loginDao);
}
