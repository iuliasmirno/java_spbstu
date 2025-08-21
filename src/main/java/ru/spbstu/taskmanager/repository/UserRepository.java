package ru.spbstu.taskmanager.repository;

import ru.spbstu.taskmanager.model.User;

import java.util.List;

public interface UserRepository {
    User save(User user);
    User findByUsername(String username);
    List<User> findAll();

    void removeAll();
}
