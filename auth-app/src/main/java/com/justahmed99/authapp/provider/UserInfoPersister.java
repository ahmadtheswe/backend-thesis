package com.justahmed99.authapp.provider;

import reactor.core.publisher.Mono;

public interface UserInfoPersister {
  Mono<Void> saveUser(final UserInfo userInfo);
}
