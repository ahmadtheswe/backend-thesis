package com.justahmed99.authapp.provider;

import reactor.core.publisher.Mono;

public interface UserInfoRetriever {
  Mono<UserInfo> getUserInfo(final String id);
  Mono<UserInfo> getUserInfoByUsername(final String username);
}
