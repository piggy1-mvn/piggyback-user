package com.incentives.piggyback.user;

import com.incentives.piggyback.user.controller.UserController;
import com.incentives.piggyback.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserApplicationTests {

	@Mock
	private UserService userService;

	@InjectMocks
	UserController userController;

	@Before
	public void setUp() {
		MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void contextLoads() {
	}

}