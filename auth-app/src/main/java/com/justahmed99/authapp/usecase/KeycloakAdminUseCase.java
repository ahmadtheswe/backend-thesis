package com.justahmed99.authapp.usecase;

import com.justahmed99.authapp.dto.LoginRequestDTO;
import com.justahmed99.authapp.dto.RegistrationRequestDTO;
import com.justahmed99.authapp.dto.ReturnDataDTO;
import com.justahmed99.authapp.dto.TokenResponseDTO;
import com.justahmed99.authapp.dto.converter.LoginConverter;
import com.justahmed99.authapp.dto.converter.RegistrationConverter;
import com.justahmed99.authapp.dto.converter.TokenDTOConverter;
import com.justahmed99.authapp.provider.KeycloakAdminPersister;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KeycloakAdminUseCase implements KeycloakAdminService {

  private final KeycloakAdminPersister keycloakAdminPersister;

  @Override
  public Mono<ResponseEntity<ReturnDataDTO<String>>> createRegularUser(
      final RegistrationRequestDTO dto) {
    return RegistrationConverter.fromRegistrationRequestDTO(dto)
        .map(keycloakAdminPersister::createRegularUser)
        .map(responsePair -> {
          if (responsePair.getLeft()) {
            return ResponseEntity.ok(
                ReturnDataDTO.<String>builder()
                    .data("SUCCESS")
                    .messages(List.of("User registered successfully!"))
                    .build());
          } else {
            List<String> errorMessages = new ArrayList<>() {{
              if (responsePair.getRight() == 500) {
                add(HttpStatus.INTERNAL_SERVER_ERROR.name());
                add("Failed to save user data");
              } else {
                add(HttpStatus.CONFLICT.name());
                add("User already exist!");
              }
            }};
            return ResponseEntity.status(
                    responsePair.getRight() == 500 ?
                        HttpStatus.INTERNAL_SERVER_ERROR :
                        HttpStatus.CONFLICT)
                .body(
                    ReturnDataDTO.<String>builder()
                        .data("FAILED")
                        .messages(errorMessages)
                        .build());
          }
        });
  }

  @Override
  public Mono<ResponseEntity<ReturnDataDTO<TokenResponseDTO>>> login(final LoginRequestDTO dto) {
    return LoginConverter.fromLoginRequestDTO(dto)
        .map(keycloakAdminPersister::login)
        .map(tokenMap -> ResponseEntity.ok(
            ReturnDataDTO.<TokenResponseDTO>builder()
                .data(TokenDTOConverter.fromTokenToTokenDTO(tokenMap))
                .messages(List.of("Login Success!"))
                .build()
        ))
        .onErrorResume(throwable -> {
          ReturnDataDTO<TokenResponseDTO> errorResponse =
              ReturnDataDTO.<TokenResponseDTO>builder()
                  .messages(List.of("Login Failed!"))
                  .build();

          return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse));
        });
  }

  @Override
  public Mono<ResponseEntity<Void>> logout() {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .cast(Jwt.class)
        .flatMap(jwt -> Mono.fromRunnable(() -> {
              keycloakAdminPersister.logout(jwt.getClaim("sub"));
            })
            .then(Mono.fromCallable(() -> ResponseEntity.ok().<Void>build())));
  }
}
