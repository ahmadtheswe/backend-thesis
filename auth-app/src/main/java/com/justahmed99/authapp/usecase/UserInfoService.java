package com.justahmed99.authapp.usecase;

import com.justahmed99.authapp.dto.ActiveUsersDTO;
import com.justahmed99.authapp.dto.ReturnDataDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface UserInfoService {
  Mono<ResponseEntity<ReturnDataDTO<ActiveUsersDTO>>> getActiveUserCount();
}
