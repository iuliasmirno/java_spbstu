package ru.spbstu.taskmanager.service;

import ru.spbstu.taskmanager.model.User;

import java.util.List;

public interface UserService {
    User register(String username);
    User login(String username);
    List<User> getAllUsers();

    void removeAllUsers();
}
