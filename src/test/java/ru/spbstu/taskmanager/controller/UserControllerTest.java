package ru.spbstu.taskmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.spbstu.taskmanager.model.User;
import ru.spbstu.taskmanager.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void registerShouldCreateUser() throws Exception {
        UUID id = UUID.randomUUID();
        User user = new User("testuser");
        user.setId(id);

        Mockito.when(userService.register("testuser"))
                .thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("testuser"))
                .andExpect(status().isCreated());
    }

    @Test
    void loginShouldReturnUserIfExists() throws Exception {
        UUID id = UUID.randomUUID();
        User created = new User("loginuser");
        created.setId(id);

        Mockito.when(userService.login("loginuser"))
                .thenReturn(created);

        mockMvc.perform(get("/users/login")
                        .param("username", "loginuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("loginuser"))
                .andExpect(jsonPath("$.id").value(id.toString())); // UUID → строка
    }

    @Test
    void loginShouldReturnNotFoundIfUserDoesNotExist() throws Exception {
        Mockito.when(userService.login(anyString()))
                .thenReturn(null);

        mockMvc.perform(get("/users/login")
                        .param("username", "unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUsersShouldReturnUsers() throws Exception {
        User user1 = new User("user1");
//        user1.setId(3L);
        User user2 = new User("user2");
//        user2.setId(4L);

        Mockito.when(userService.getAllUsers())
                .thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].username").value("user2"));
    }
}

