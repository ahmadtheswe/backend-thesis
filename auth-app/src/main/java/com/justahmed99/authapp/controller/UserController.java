package com.justahmed99.authapp.controller;

import com.justahmed99.authapp.dto.LoginRequestDTO;
import com.justahmed99.authapp.dto.RefreshTokenRequestDTO;
import com.justahmed99.authapp.dto.RegistrationRequestDTO;
import com.justahmed99.authapp.dto.ReturnDataDTO;
import com.justahmed99.authapp.dto.TokenResponseDTO;
import com.justahmed99.authapp.usecase.KeycloakAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final KeycloakAdminService keycloakAdminService;

  @PostMapping("/registration")
  public Mono<ResponseEntity<ReturnDataDTO<String>>> registerUser(
      @RequestBody final RegistrationRequestDTO registrationRequestDTO) {
    return keycloakAdminService.createRegularUser(registrationRequestDTO);
  }

  @PutMapping("/activate/{userId}")
  public Mono<ResponseEntity<ReturnDataDTO<String>>> activateUser(
      @PathVariable final String userId
  ) {
    return keycloakAdminService.activateUser(userId);
  }

  @PostMapping("/login")
  public Mono<ResponseEntity<ReturnDataDTO<TokenResponseDTO>>> login(
      @RequestBody final LoginRequestDTO loginRequestDTO) {
    return keycloakAdminService.login(loginRequestDTO);
  }

  @PostMapping("/refresh")
  public Mono<ResponseEntity<ReturnDataDTO<TokenResponseDTO>>> refreshToken(
      @RequestBody final RefreshTokenRequestDTO refreshTokenRequestDTO) {
    return keycloakAdminService.refresh(refreshTokenRequestDTO);
  }

  @GetMapping("/logout")
  public Mono<ResponseEntity<Void>> logout() {
    return keycloakAdminService.logout();
  }
}
