package ru.spbstu.taskmanager.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.spbstu.taskmanager.model.User;
import ru.spbstu.taskmanager.repository.UserRepository;
import ru.spbstu.taskmanager.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void registerUserShouldSave() {
        User user = new User("username");
        when(repository.save(any(User.class))).thenReturn(user);

        User created = service.register("username");

        assertEquals("username", created.getUsername());
        verify(repository).save(any(User.class));
    }

    @Test
    void loginShouldReturnUser() {
        User user = new User("username");
        when(repository.findByUsername("username")).thenReturn(user);

        User result = service.login("username");

        assertNotNull(result);
        assertEquals("username", result.getUsername());
    }
}

