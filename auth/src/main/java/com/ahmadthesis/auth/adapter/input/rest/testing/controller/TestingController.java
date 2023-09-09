package com.ahmadthesis.auth.adapter.input.rest.testing.controller;

import com.ahmadthesis.auth.adapter.input.rest.common.dto.DataResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("testing")
public class TestingController {
  @GetMapping("")
  public Mono<ResponseEntity<DataResponse<String>>> testing() {
    List<String> message = List.of("hello", "world");
    return Mono.just(ResponseEntity.ok(new DataResponse<>(null, message)));
  }
}
