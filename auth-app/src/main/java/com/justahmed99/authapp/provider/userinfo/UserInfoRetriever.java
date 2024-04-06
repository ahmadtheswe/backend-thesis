package com.justahmed99.authapp.provider.userinfo;

import reactor.core.publisher.Mono;

public interface UserInfoRetriever {
  Mono<UserInfo> getUserInfo(final String id);
  Mono<UserInfo> getUserInfoByUsername(final String username);

  Mono<Integer> getUsersCount();
}
