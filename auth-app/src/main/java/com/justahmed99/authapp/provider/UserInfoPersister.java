package com.justahmed99.authapp.provider;

import reactor.core.publisher.Mono;

public interface UserInfoPersister {
  Mono<UserInfo> saveUser(final UserInfo userInfo, Boolean isNew);
}
