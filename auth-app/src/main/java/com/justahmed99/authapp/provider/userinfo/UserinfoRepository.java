package com.justahmed99.authapp.provider.userinfo;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserinfoRepository extends R2dbcRepository<UserInfoEntity, String> {
  Mono<UserInfoEntity> findUserInfoEntityByUsername(String username);

  Mono<Integer> countAllByRole(String role);
}
