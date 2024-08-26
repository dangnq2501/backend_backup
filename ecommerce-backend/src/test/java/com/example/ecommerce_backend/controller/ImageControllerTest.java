package com.example.ecommerce_backend.controller;

import com.example.ecommerce_backend.dto.request.ImageRequest;
import com.example.ecommerce_backend.dto.request.UserCreationRequest;
import com.example.ecommerce_backend.dto.request.UserUpdateRequest;
import com.example.ecommerce_backend.dto.response.ImageResponse;
import com.example.ecommerce_backend.dto.response.UserResponse;
import com.example.ecommerce_backend.service.ImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class ImageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;
    private UserCreationRequest userCreationRequest;
    private UserCreationRequest adminCreationRequest;
    private UserCreationRequest userCreationRequestInvalidUsername;
    private UserCreationRequest userCreationRequestInvalidPassword;
    private UserUpdateRequest userUpdateRequest;
    private UserResponse userResponse;
    private Set<String> user_roles = new HashSet<>();
    private Set<String> admin_roles = new HashSet<>();
    private ImageRequest imageRequest;
    private ImageResponse imageResponse;

    @BeforeEach
    void initData(){
        admin_roles.add("ADMOIN");


        adminCreationRequest = UserCreationRequest.builder()
                .username("admin")
                .password("password123")
                .email("admin@gmail.com")
                .phone("0912345876")
                .roles(admin_roles)
                .build();


        userResponse = UserResponse.builder()
                .username("quydang")
                .password("password123")
                .email("quydang@gmail.com")
                .phone("0912345678")
                .build();
        imageRequest = ImageRequest.builder()
                .pathImage("/woman/dress/1/123.jpg")
                .pathId("/woman/dress/1")
                .build();
        imageResponse = ImageResponse.builder()
                .pathImage("/woman/dress/1/123.jpg")
                .pathId("/woman/dress/1")
                .build();
    }

    @Test
    @WithMockUser(username="admin")
    void createValidImage() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(imageRequest);
        Mockito.when(imageService.create(ArgumentMatchers.any())).thenReturn(imageResponse);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/image")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.pathImage").value("/woman/dress/1/123.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.pathId").value("/woman/dress/1")
                );
    }
}
