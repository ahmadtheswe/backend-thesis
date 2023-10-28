package com.justahmed99.authapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
  @GetMapping("/admin")
  public Mono<String> admin() {
    return Mono.just("this is admin");
  }

  @GetMapping("/regular")
  public Mono<String> regular() {
    return Mono.just("this is regular");
  }

  @GetMapping("/public")
  public Mono<String> publicEndPoint() {
    return Mono.just("this is public");
  }
}
