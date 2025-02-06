package com.example.RegisterationSystem.Controller;

import com.example.RegisterationSystem.Model.Userentity;
import com.example.RegisterationSystem.Repository.UserentityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterationControllerTest {
    @BeforeAll
    static void loadEnv() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private UserentityRepository userentityRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createUser_ValidUser_ReturnsCreated() throws Exception {
        Userentity user = new Userentity();
        user.setUsername("john_doe");
        user.setPassword("password123");
        user.setEmail("john@example.com");

        Mockito.when(passwordEncoder.encode(any(String.class))).thenReturn("encoded_password");
        Mockito.when(userentityRepository.save(any(Userentity.class))).thenReturn(user);

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void createUser_DuplicateUsername_ReturnsConflict() throws Exception {
        Userentity user = new Userentity();
        user.setUsername("john_doe");
        user.setPassword("password123");
        user.setEmail("john@example.com");

        Mockito.when(passwordEncoder.encode(any(String.class))).thenReturn("encoded_password");
        Mockito.when(userentityRepository.save(any(Userentity.class)))
                .thenThrow(new org.springframework.dao.DataIntegrityViolationException("Duplicate username"));

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Username already exists. Please choose another one."));
    }

    @Test
    void createUser_UnexpectedError_ReturnsInternalServerError() throws Exception {
        Userentity user = new Userentity();
        user.setUsername("john_doe");
        user.setPassword("password123");
        user.setEmail("john@example.com");

        Mockito.when(passwordEncoder.encode(any(String.class))).thenReturn("encoded_password");
        Mockito.when(userentityRepository.save(any(Userentity.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An unexpected error occurred."));
    }
}
