package com.example.ecommerce_backend.controller;

import com.example.ecommerce_backend.dto.request.UserCreationRequest;
import com.example.ecommerce_backend.dto.request.UserUpdateRequest;
import com.example.ecommerce_backend.dto.response.UserResponse;
import com.example.ecommerce_backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.mockito.Mockito;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc

public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    private UserCreationRequest userCreationRequest;
    private UserCreationRequest adminCreationRequest;
    private UserCreationRequest userCreationRequestInvalidUsername;
    private UserCreationRequest userCreationRequestInvalidPassword;
    private UserUpdateRequest userUpdateRequest;
    private UserResponse userResponse;
    private Set<String> user_roles = new HashSet<>();
    private Set<String> admin_roles = new HashSet<>();

    @BeforeEach
    void initData(){
        user_roles.add("USER");
        admin_roles.add("ADMOIN");

        userCreationRequest = UserCreationRequest.builder()
                .username("quydang")
                .password("password123")
                .email("quydang@gmail.com")
                .phone("0912345678")
                .roles(user_roles)
                .build();

        adminCreationRequest = UserCreationRequest.builder()
                .username("admin")
                .password("password123")
                .email("admin@gmail.com")
                .phone("0912345876")
                .roles(admin_roles)
                .build();

        userCreationRequestInvalidUsername = UserCreationRequest.builder()
                .username("quy")
                .password("password123")
                .email("quydang@gmail.com")
                .phone("0912345678")
                .roles(user_roles)
                .build();

        userCreationRequestInvalidPassword = UserCreationRequest.builder()
                .username("quydang")
                .password("123")
                .email("quydang@gmail.com")
                .phone("0912345678")
                .roles(user_roles)
                .build();

        userUpdateRequest = UserUpdateRequest.builder()
                .password("quydang123")
                .email("quydang123@gmail.com")
                .phone("0912678345")
                .roles(user_roles)
                .build();

        userResponse = UserResponse.builder()
                .username("quydang")
                .password("password123")
                .email("quydang@gmail.com")
                .phone("0912345678")
                .build();
    }

    @Test
    void createValidUser() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userCreationRequest);
        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.username").value("quydang"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.email").value("quydang@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.phone").value("0912345678")
                );
    }

    @Test
    void createUserInvalidPassword() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userCreationRequestInvalidPassword);
        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("202"));
    }

    @Test
    void createUserInvalidUsername() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userCreationRequestInvalidUsername);
        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("201"));
    }

    @Test
    @WithMockUser(username="quydang", password = "passwrod")
    void updateUserValid() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userUpdateRequest);
        Mockito.when(userService.updateUser(ArgumentMatchers.any())).thenReturn(userResponse);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/user")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000"));
    }

    @Test
    @WithMockUser(username="admin")
    void getAllUser() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(adminCreationRequest);
        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/getAll")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000")
                );
    }

    @Test
    @WithMockUser(username="quydang")
    void getMyInfo() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userCreationRequest);
        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/my-info")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000")
                );
    }




}
