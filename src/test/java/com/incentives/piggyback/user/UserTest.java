package com.incentives.piggyback.user;

import com.incentives.piggyback.user.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class UserTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void createUser() throws Exception {
        String uri = "/piggyback-user/user";
        User user = new User();
       // user.setId(Long.valueOf(30));
        user.setFirst_name("JunitTesting");
        user.setMobile_number("+919986927698");
        user.setUser_password("Password123");
        user.setMobile_verified(true);
        user.setUser_email("abc@gmail.com");
        user.setDevice_id("adcvcb123");
        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }
    @Test
    public void getUserList() throws Exception {
        String uri = "/piggyback-user/user";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        User[] usersList = super.mapFromJson(content, User[].class);
        assertTrue(usersList.length > 0);
    }
    @Test
    public void updateUser() throws Exception {
        String uri = "/piggyback-user/user/2";
        User user = new User();
        user.setId(Long.valueOf(2));
        user.setFirst_name("JunitTesting");
        user.setMobile_number("+919986927698");
        user.setUser_password("Password1234");
        user.setMobile_verified(true);
        user.setUser_email("abc@gmail.com");
        user.setDevice_id("adcvcb123");
        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
    @Test
    public void deleteUser() throws Exception {
        String uri = "/piggyback-user/user/13";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
    }

    @Test
    public void createUserFailForInvalidInput() throws Exception {
        String uri = "/piggyback-user/user";
        User user = new User();
        // user.setId(Long.valueOf(30));
        user.setFirst_name("JunitTesting");
        user.setUser_password("Password123");
        user.setMobile_verified(true);
        user.setUser_email("abc@gmail.com");
        user.setDevice_id("adcvcb123");
        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "{\"mobile_number\":\"Mobile Number is mandatory\"}");
    }

    @Test
    public void createUserFailForInvalidInputEmailId() throws Exception {
        String uri = "/piggyback-user/user";
        User user = new User();
        // user.setId(Long.valueOf(30));
        user.setFirst_name("JunitTesting");
        user.setUser_password("Password123");
        user.setMobile_number("+919986927698");
        user.setMobile_verified(true);
        user.setUser_email("abc@");
        user.setDevice_id("adcvcb123");
        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "{\"user_email\":\"must be a well-formed email address\"}");
    }

    @Test
    public void getUserListInvalidUserId() throws Exception {
        String uri = "/piggyback-user/user/0";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Could not find user with Id 0");
    }

    @Test
    public void updateUserInvalidId() throws Exception {
        String uri = "/piggyback-user/user/0";
        User user = new User();
        user.setId(Long.valueOf(2));
        user.setFirst_name("JunitTesting");
        user.setMobile_number("+919986927698");
        user.setUser_password("Password1234");
        user.setMobile_verified(true);
        user.setUser_email("abc@gmail.com");
        user.setDevice_id("adcvcb123");
        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }
    @Test
    public void deleteUserInvalidId() throws Exception {
        String uri = "/piggyback-user/user/0";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404,status);
    }
}
