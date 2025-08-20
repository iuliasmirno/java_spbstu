package ru.spbstu.taskmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.taskmanager.model.User;
import ru.spbstu.taskmanager.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<User> register(@RequestBody String username) {
        User user = service.register(username);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }

    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username) {
        User user = service.login(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }
}
