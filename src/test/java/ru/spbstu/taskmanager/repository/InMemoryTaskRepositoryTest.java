package ru.spbstu.taskmanager.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.repository.impl.InMemoryTaskRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskRepositoryTest {

    private InMemoryTaskRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTaskRepository();
    }

    @Test
    void saveShouldStoreTask() {
        Task task = new Task("user1", "Test Task", LocalDate.now());
        repository.save(task);

        List<Task> tasks = repository.findByUserId("user1");
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
    }

    @Test
    void findByUserShouldReturnOnlyThatUserIdTasks() {
        Task t1 = new Task("user1", "Task1", LocalDate.now());
        Task t2 = new Task("user2", "Task2", LocalDate.now());
        repository.save(t1);
        repository.save(t2);

        List<Task> tasks = repository.findByUserId("user1");

        assertEquals(1, tasks.size());
        assertEquals("Task1", tasks.get(0).getTitle());
    }

    @Test
    void markDeletedShouldSetDeletedFlag() {
        Task task = new Task("user1", "Task", LocalDate.now());
        repository.save(task);

        boolean result = repository.markDeleted("user1", task.getId());

        assertTrue(result);
        assertTrue(task.isDeleted());
    }

    @Test
    void markDeletedShouldReturnFalseIfWrongUser() {
        Task task = new Task("user1", "Task", LocalDate.now());
        repository.save(task);

        boolean result = repository.markDeleted("user2", task.getId());

        assertFalse(result);
        assertFalse(task.isDeleted());
    }
}

