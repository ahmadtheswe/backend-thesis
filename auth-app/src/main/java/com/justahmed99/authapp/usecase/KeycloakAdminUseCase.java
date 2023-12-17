package com.justahmed99.authapp.usecase;

import com.justahmed99.authapp.dto.LoginRequestDTO;
import com.justahmed99.authapp.dto.RefreshTokenRequestDTO;
import com.justahmed99.authapp.dto.RegistrationRequestDTO;
import com.justahmed99.authapp.dto.ReturnDataDTO;
import com.justahmed99.authapp.dto.TokenResponseDTO;
import com.justahmed99.authapp.dto.converter.LoginConverter;
import com.justahmed99.authapp.dto.converter.RefreshTokenConverter;
import com.justahmed99.authapp.dto.converter.RegistrationConverter;
import com.justahmed99.authapp.dto.converter.TokenDTOConverter;
import com.justahmed99.authapp.provider.KeycloakAdminPersister;
import com.justahmed99.authapp.provider.UserException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
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
        .flatMap(userRepresentation -> {
          try {
            final UserRepresentation createdUser = keycloakAdminPersister.createRegularUser(
                userRepresentation);
            return Mono.just(ResponseEntity.ok(
                ReturnDataDTO.<String>builder()
                    .data("SUCCESS")
                    .messages(List.of("User registered successfully!"))
                    .build()));
          } catch (UserException e) {
            return Mono.just(ResponseEntity.status(e.getStatusCode()).body(
                ReturnDataDTO.<String>builder()
                    .data("FAILED")
                    .messages(List.of(e.getMessage()))
                    .build()));
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
  public Mono<ResponseEntity<ReturnDataDTO<TokenResponseDTO>>> refresh(
      RefreshTokenRequestDTO dto) {
    return RefreshTokenConverter.fromRefreshTokenRequestDTO(dto)
        .map(keycloakAdminPersister::refresh)
        .map(tokenMap -> ResponseEntity.ok(
            ReturnDataDTO.<TokenResponseDTO>builder()
                .data(TokenDTOConverter.fromTokenToTokenDTO(tokenMap))
                .messages(List.of("Refresh Success!"))
                .build()
        ))
        .onErrorResume(throwable -> {
          ReturnDataDTO<TokenResponseDTO> errorResponse =
              ReturnDataDTO.<TokenResponseDTO>builder()
                  .messages(List.of("Refresh Failed!"))
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
