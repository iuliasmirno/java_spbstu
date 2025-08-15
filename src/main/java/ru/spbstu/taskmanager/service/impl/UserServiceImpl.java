package ru.spbstu.taskmanager.service.impl;

import org.springframework.stereotype.Service;
import ru.spbstu.taskmanager.model.User;
import ru.spbstu.taskmanager.repository.UserRepository;
import ru.spbstu.taskmanager.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User register(String username) {
        if (repository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        return repository.save(new User(username));
    }

    @Override
    public User login(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }
}
