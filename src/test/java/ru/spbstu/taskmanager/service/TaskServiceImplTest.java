package ru.spbstu.taskmanager.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.repository.TaskRepository;
import ru.spbstu.taskmanager.service.impl.TaskServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private TaskServiceImpl service;

    @Test
    void createTaskShouldSaveAndReturnTask() {
        Task task = new Task("user1", "Test Task", LocalDate.now());
        when(repository.save(any(Task.class))).thenReturn(task);

        Task created = service.createTask("user1", "Test Task", LocalDate.now());

        assertEquals("Test Task", created.getTitle());
        verify(repository, times(1)).save(any(Task.class));
    }

    @Test
    void getAllTasksShouldReturnNonDeleted() {
        Task t1 = new Task("user1", "Task 1", LocalDate.now());
        Task t2 = new Task("user1", "Task 2", LocalDate.now());
        t2.setDeleted(true);
        when(repository.findByUserId("user1")).thenReturn(List.of(t1, t2));

        List<Task> result = service.getAllTasks("user1");

        assertEquals(1, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
    }
}
