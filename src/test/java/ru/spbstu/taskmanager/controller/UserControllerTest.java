package ru.spbstu.taskmanager.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.spbstu.taskmanager.model.User;
import ru.spbstu.taskmanager.service.UserService;

import java.util.List;

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
    void register_shouldReturnCreatedUser() throws Exception {
        User user = new User("john");
        when(userService.register("john")).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("john"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/users/" + user.getId()))
                .andExpect(jsonPath("$.username").value("john"));
    }

    @Test
    void login_shouldReturnUserIfExists() throws Exception {
        User user = new User("mary");
        when(userService.login("mary")).thenReturn(user);

        mockMvc.perform(get("/users/login")
                        .param("username", "mary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("mary"));
    }

    @Test
    void login_shouldReturnNotFoundIfUserDoesNotExist() throws Exception {
        when(userService.login("ghost")).thenReturn(null);

        mockMvc.perform(get("/users/login")
                        .param("username", "ghost"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(new User("a"), new User("b")));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("a"))
                .andExpect(jsonPath("$[1].username").value("b"));
    }
}

