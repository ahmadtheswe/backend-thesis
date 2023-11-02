package com.justahmed99.authapp.usecase;

import com.justahmed99.authapp.dto.LoginRequestDTO;
import com.justahmed99.authapp.dto.RegistrationRequestDTO;
import com.justahmed99.authapp.dto.ReturnDataDTO;
import com.justahmed99.authapp.dto.TokenResponseDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface KeycloakAdminService {
  Mono<ResponseEntity<ReturnDataDTO<String>>> createRegularUser(final RegistrationRequestDTO dto);
  Mono<ResponseEntity<ReturnDataDTO<TokenResponseDTO>>> login(final LoginRequestDTO dto);
  Mono<ResponseEntity<Void>> logout();
}
