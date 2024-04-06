package com.justahmed99.authapp.controller;

import com.justahmed99.authapp.dto.ActiveUsersDTO;
import com.justahmed99.authapp.dto.ReturnDataDTO;
import com.justahmed99.authapp.usecase.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user/admin")
@RequiredArgsConstructor
public class AdminController {
  private final UserInfoService userInfoService;

  @GetMapping("/active-users")
  Mono<ResponseEntity<ReturnDataDTO<ActiveUsersDTO>>> getActiveUsers() {
    return userInfoService.getActiveUserCount();
  }
}
