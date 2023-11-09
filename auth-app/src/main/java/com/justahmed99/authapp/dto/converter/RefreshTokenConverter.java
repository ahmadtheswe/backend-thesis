package com.justahmed99.authapp.dto.converter;

import com.justahmed99.authapp.dto.RefreshTokenRequestDTO;
import reactor.core.publisher.Mono;

public final class RefreshTokenConverter {
  public static Mono<String> fromRefreshTokenRequestDTO(final RefreshTokenRequestDTO dto) {
    return Mono.just(dto.getRefreshToken());
  }
}
