package com.ahmadthesis.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImageApplicationTests {
	@Test
	void contextLoads() {
		ImageApplication.main(new String[] {});
		Assertions.assertTrue(true);
	}
}
