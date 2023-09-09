package com.ahmadthesis.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthApplicationTests {

  @Test
  void contextLoads() {
    AuthApplication.main(new String[]{});
    Assertions.assertTrue(true);
  }

}
