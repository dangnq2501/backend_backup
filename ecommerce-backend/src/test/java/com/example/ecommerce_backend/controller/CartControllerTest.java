package com.example.ecommerce_backend.controller;

import com.example.ecommerce_backend.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

}
