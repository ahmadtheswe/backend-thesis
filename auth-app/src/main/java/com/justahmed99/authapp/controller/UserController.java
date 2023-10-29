package com.justahmed99.authapp.controller;

import com.justahmed99.authapp.dto.LoginRequestDTO;
import com.justahmed99.authapp.dto.RegistrationRequestDTO;
import com.justahmed99.authapp.dto.ReturnDataDTO;
import com.justahmed99.authapp.usecase.KeycloakAdminService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
      @RequestBody RegistrationRequestDTO registrationRequestDTO) {
    return keycloakAdminService.createRegularUser(registrationRequestDTO);
  }

  @PostMapping("/login")
  public Mono<ResponseEntity<ReturnDataDTO<Map<String, String>>>> login(
      @RequestBody LoginRequestDTO loginRequestDTO) {
    return keycloakAdminService.login(loginRequestDTO);
  }

  @GetMapping("/logout")
  public Mono<ResponseEntity<Void>> logout() {
    return keycloakAdminService.logout();
  }
}
