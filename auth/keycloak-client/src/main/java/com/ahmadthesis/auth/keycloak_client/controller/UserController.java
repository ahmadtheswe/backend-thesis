package com.ahmadthesis.auth.keycloak_client.controller;

import com.ahmadthesis.auth.keycloak_client.dao.DataResponse;
import com.ahmadthesis.auth.keycloak_client.dao.LoginDao;
import com.ahmadthesis.auth.keycloak_client.dao.LoginResponse;
import com.ahmadthesis.auth.keycloak_client.usecase.WebClientUseCase;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class UserController {

  private final WebClientUseCase useCase;

  public UserController(
      WebClientUseCase useCase
  ) {
    this.useCase = useCase;
  }

  @PostMapping("login")
  public Mono<ResponseEntity<DataResponse<LoginResponse>>> login(
      @RequestBody LoginDao loginRequest
  ) {
    return useCase.login(loginRequest)
        .mapNotNull(loginResponse ->
            new ResponseEntity<>(
                new DataResponse<>(loginResponse, new ArrayList<>()),
                HttpStatus.OK
            )
        );
  }
}
