package com.ahmadthesis.auth.keycloak_client.usecase;

import com.ahmadthesis.auth.keycloak_client.converter.LoginConverter;
import com.ahmadthesis.auth.keycloak_client.dao.KeycloakTokenResponse;
import com.ahmadthesis.auth.keycloak_client.dao.LoginDao;
import com.ahmadthesis.auth.keycloak_client.dao.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WebClientUseCaseImpl implements
    WebClientUseCase {
  private final LoginConverter converter;

  @Value("${keycloak.url}")
  private String keycloakUrl;

  public WebClientUseCaseImpl(
      LoginConverter converter
  ) {
    this.converter = converter;
  }

  @Override
  public Mono<LoginResponse> login(final LoginDao loginDao) {
    final WebClient client = WebClient.create(keycloakUrl);
    final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("username", loginDao.getUsername());
    formData.add("password", loginDao.getPassword());
    formData.add("grant_type", "password");
    formData.add("client_id", "image-keycloak-springboot");
    formData.add("client_secret", "XHabFbfDNgPN8Q65HDE9ns08cK9p8BcC");
    formData.add("scope", "openid");

    return client.post()
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData(formData))
        .retrieve()
        .bodyToMono(KeycloakTokenResponse.class)
        .mapNotNull(converter::convert);
  }
}
