package com.justahmed99.authapp.provider;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserinfoRepository extends R2dbcRepository<UserInfoEntity, String> {
  Mono<UserInfoEntity> findUserInfoEntityByUsername(String username);
}
