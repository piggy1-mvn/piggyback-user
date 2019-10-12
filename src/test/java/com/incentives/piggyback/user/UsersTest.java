package com.incentives.piggyback.user;

import com.incentives.piggyback.user.controller.UserController;
import com.incentives.piggyback.user.model.Users;
import com.incentives.piggyback.user.repository.UserServiceRepository;
import com.incentives.piggyback.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UsersTest {

    private MockMvc mvc;

    @Mock
    private UserService userService;

    @Mock
    private UserServiceRepository userServiceRepo;

    @InjectMocks
    UserController userController;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
        Users user = new Users();
        user.setId(1L);
        user.setFirst_name("JunitTesting");
        user.setMobile_number("+919986927698");
        user.setUser_password("Password123");
        user.setMobile_verified(true);
        user.setEmail("abc@gmail.com");
        user.setUser_role("PIGGY_ADMIN");
        user.setDevice_id("adcvcb123");
    }

//    @Test
//    public final void testCreateUser() throws Exception {
//        String userJson = "{\"id\":\"1\",\"first_name\":\"JunitTesting\",\"user_password\":\"Password123\",\"mobile_number\":\"+919986927698\",\"mobile_verified\":true,\"user_email\":\"abc@gmail.com\",\"user_role\": \"PIGGY_ADMIN\",\"device_id\":\"adcvcb123\"}";
//      //  Mockito.when(userServiceRepo.save(any(User.class))).thenReturn(user);
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/user")
//                .accept(MediaType.APPLICATION_JSON).content(userJson)
//                .contentType(MediaType.APPLICATION_JSON);
//        MvcResult result = mvc.perform(requestBuilder).andReturn();
//        MockHttpServletResponse response = result.getResponse();
//        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//    }

    @Test
    public final void TestGetAllUser() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//        Mockito.when(userServiceRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/{id}", 1)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public final void TestGetUserById() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public final void TestDeleteUser() throws Exception {
//        Mockito.when(userServiceRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));
//        Mockito.doNothing().when(userServiceRepo).deleteById(1L);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/user/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public final void TestUpdateUser() throws Exception {
        String userJson = "{\"id\":\"1\",\"first_name\":\"JunitTesting\",\"user_password\":\"Password123\",\"mobile_number\":\"+919986927698\",\"mobile_verified\":true,\"email\":\"abc@gmail.com\",\"user_role\": \"PIGGY_ADMIN\",\"device_id\":\"adcvcb123\"}";
  //      Mockito.when(userServiceRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));
  //       Mockito.when(userServiceRepo.save(user)).thenReturn(user);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/user/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}