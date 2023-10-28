package com.justahmed99.authapp.usecase;

import com.justahmed99.authapp.dto.LoginRequestDTO;
import com.justahmed99.authapp.dto.RegistrationRequestDTO;
import com.justahmed99.authapp.dto.ReturnDataDTO;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface KeycloakAdminService {
  Mono<ResponseEntity<ReturnDataDTO<String>>> createRegularUser(RegistrationRequestDTO dto);
  Mono<ResponseEntity<ReturnDataDTO<Map<String, String>>>> login(LoginRequestDTO dto);
}
