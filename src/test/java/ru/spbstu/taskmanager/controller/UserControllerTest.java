package ru.spbstu.taskmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setup() {
        userService.removeAllUsers();
    }

    @Test
    void registerShouldCreateUser() {
        String username = "testuser";

        ResponseEntity<User> response = restTemplate.postForEntity("/users", username, User.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(username, response.getBody().getUsername());
        assertNotNull(response.getBody().getId());
    }

    @Test
    void loginShouldReturnUserIfExists() {
        User created = userService.register("loginuser");

        ResponseEntity<User> response = restTemplate.getForEntity("/users/login?username=loginuser", User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(created.getUsername(), response.getBody().getUsername());
        assertEquals(created.getId(), response.getBody().getId());
    }

    @Test
    void loginShouldReturnNotFoundIfUserDoesNotExist() {
        ResponseEntity<User> response = restTemplate.getForEntity("/users/login?username=unknown", User.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllUsersShouldReturnUsers() {
        User user1 = userService.register("user1");
        User user2 = userService.register("user2");

        ResponseEntity<User[]> response = restTemplate.getForEntity("/users", User[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<User> users = Arrays.asList(response.getBody());
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("user1")));
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("user2")));
    }
}

