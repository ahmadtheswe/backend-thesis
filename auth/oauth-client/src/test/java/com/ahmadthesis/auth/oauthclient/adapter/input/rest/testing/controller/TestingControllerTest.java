package com.ahmadthesis.auth.oauthclient.adapter.input.rest.testing.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class TestingControllerTest {
  private WebTestClient webTestClient;

  @BeforeEach
  void setup() {
    TestingController controller = new TestingController();
    webTestClient = WebTestClient.bindToController(controller).build();
  }

  @Test
  @DisplayName("Testing test controller")
  void testingTestController() {
    // Arrange
    String url = "/testing";

    // Act

    // Assert
    webTestClient.get()
        .uri(url)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.data").isEmpty()
        .jsonPath("$.messages").isArray()
        .jsonPath("$.messages[0]").isEqualTo("hello")
        .jsonPath("$.messages[1]").isEqualTo("world");
  }
}