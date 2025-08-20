package ru.spbstu.taskmanager.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.spbstu.taskmanager.model.User;
import ru.spbstu.taskmanager.repository.impl.InMemoryUserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    private InMemoryUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();
    }

    @Test
    void save_shouldStoreUser() {
        User user = new User("username");
        repository.save(user);

        User found = repository.findByUsername("username");

        assertNotNull(found);
        assertEquals("username", found.getUsername());
    }

    @Test
    void findByUsername_shouldReturnNullIfNotExists() {
        User result = repository.findByUsername("ghost");
        assertNull(result);
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        repository.save(new User("username"));
        repository.save(new User("username"));

        List<User> all = repository.findAll();

        assertEquals(2, all.size());
    }
}
