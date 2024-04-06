package com.justahmed99.authapp.usecase;

import com.justahmed99.authapp.dto.ActiveUsersDTO;
import com.justahmed99.authapp.dto.ReturnDataDTO;
import com.justahmed99.authapp.dto.TokenResponseDTO;
import com.justahmed99.authapp.provider.userinfo.UserInfoRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserInfoUseCase implements UserInfoService {

  private final UserInfoRetriever userInfoRetriever;

  @Override
  public Mono<ResponseEntity<ReturnDataDTO<ActiveUsersDTO>>> getActiveUserCount() {
    return userInfoRetriever.getUsersCount().flatMap(activeUserCount -> {
      return Mono.just(ResponseEntity.ok(
          ReturnDataDTO.<ActiveUsersDTO>builder()
              .data(ActiveUsersDTO.builder().activeUsers(activeUserCount).build())
              .build()
      ));
    });
  }
}
