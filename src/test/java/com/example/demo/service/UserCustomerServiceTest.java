package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.model.UserCustomer;
import com.example.demo.repository.UserCustomerRepository;

class UserCustomerServiceTest {
	
	@Autowired
	private UserCustomerService userService;
	
	@MockBean
	private UserCustomerRepository userRepository;
	
	private UserCustomer user;

	@BeforeEach
	void setUp() throws Exception {
		user = UserCustomer.builder()
				.address("Haifa Hamegenim 55")
				.age(30)
				.build();
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
