package com.hms.controller;

import com.hms.configuration.JWTFilter;
import com.hms.entity.AppUser;
import com.hms.payload.UserDto;
import com.hms.repository.AppUserRepository;
import com.hms.service.JWTService;
import com.hms.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private AppUserRepository appUserRepository;

    private UserDto userDto;


    @BeforeEach
    void setUp() {
        UserDto userDto = new UserDto("user", "testUser", "test@example.com", "password", "ROLE_USER");
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(userService);
    }

    @Test
    void testCreateUser() throws Exception {
    }


    @Test
    void testCreatePropertyOwnerUser() throws Exception {
    }

    @Test
    void testGetUsers() throws Exception {

    }

    @Test
    void testDeleteUser() throws Exception {
    }

    @Test
    void testUpdateUser() throws Exception {
    }

    @Test
    void testGetUserById() throws Exception {
    }
}
