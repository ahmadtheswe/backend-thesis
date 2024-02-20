package com.justahmed99.authapp.provider.userinfo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserInfoProvider implements UserInfoPersister, UserInfoRetriever {

  private final UserinfoRepository repository;

  @Override
  public Mono<UserInfo> saveUser(UserInfo userInfo, Boolean isNew) {
    final UserInfoEntity entity = UserInfoConverter.toEntity(userInfo);
    entity.setIsNew(isNew);
    return repository.save(entity).map(UserInfoConverter::toDomain);
  }

  @Override
  public Mono<UserInfo> getUserInfo(String id) {
    return repository.findById(id).map(UserInfoConverter::toDomain);
  }

  @Override
  public Mono<UserInfo> getUserInfoByUsername(String username) {
    return repository.findUserInfoEntityByUsername(username).map(UserInfoConverter::toDomain);
  }
}
