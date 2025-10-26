package com.scm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.services.EmailService;

@SpringBootTest
class ScmApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService service;

	@Test
	void sendEmailTest() {
		// service.sendEmail("0506cs221194@gmail.com", "jus testing email service",
		// "this is scm project working on email service");

		service.sendEmail("sahusudeepkumar3@gmail.com", "jus testing email service",
				"this is scm project working on email service");
	}
}