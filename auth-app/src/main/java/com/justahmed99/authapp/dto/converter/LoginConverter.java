package com.justahmed99.authapp.dto.converter;

import com.justahmed99.authapp.business.Login;
import com.justahmed99.authapp.dto.LoginRequestDTO;
import reactor.core.publisher.Mono;

public final class LoginConverter {

  public static Mono<Login> fromLoginRequestDTO(final LoginRequestDTO dto) {
    return Mono.just(
        Login.builder()
            .username(dto.getUsername())
            .password(dto.getPassword())
            .build());
  }
}
