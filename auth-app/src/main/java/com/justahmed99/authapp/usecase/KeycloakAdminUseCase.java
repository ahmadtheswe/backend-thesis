package com.justahmed99.authapp.usecase;

import com.justahmed99.authapp.dto.LoginRequestDTO;
import com.justahmed99.authapp.dto.RegistrationRequestDTO;
import com.justahmed99.authapp.dto.ReturnDataDTO;
import com.justahmed99.authapp.dto.converter.LoginConverter;
import com.justahmed99.authapp.dto.converter.RegistrationConverter;
import com.justahmed99.authapp.provider.KeycloakAdminPersister;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KeycloakAdminUseCase implements KeycloakAdminService {

  private final KeycloakAdminPersister keycloakAdminPersister;

  @Override
  public Mono<ResponseEntity<ReturnDataDTO<String>>> createRegularUser(RegistrationRequestDTO dto) {
    return RegistrationConverter.fromRegistrationRequestDTO(dto)
        .map(keycloakAdminPersister::createRegularUser)
        .map(success -> {
          if (success) {
            return ResponseEntity.ok(
                ReturnDataDTO.<String>builder()
                    .data("SUCCESS")
                    .messages(List.of("User registered successfully!"))
                    .build());
          } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    ReturnDataDTO.<String>builder()
                        .data("FAILED")
                        .messages(List.of(HttpStatus.INTERNAL_SERVER_ERROR.name(),
                            "User registration failed!"))
                        .build());
          }
        });
  }

  @Override
  public Mono<ResponseEntity<ReturnDataDTO<Map<String, String>>>> login(LoginRequestDTO dto) {
    return LoginConverter.fromLoginRequestDTO(dto)
        .map(keycloakAdminPersister::login)
        .map(tokenMap -> ResponseEntity.ok(
            ReturnDataDTO.<Map<String, String>>builder()
                .data(tokenMap)
                .messages(List.of("Login Success!"))
                .build()
        ))
        .onErrorResume(throwable -> {
          ReturnDataDTO<Map<String, String>> errorResponse =
              ReturnDataDTO.<Map<String, String>>builder()
                  .messages(List.of("Login Failed!"))
                  .build();

          return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse));
        });
  }
}
