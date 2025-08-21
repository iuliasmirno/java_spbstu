package ru.spbstu.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.spbstu.taskmanager.dto.CreateTaskRequest;
import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.service.NotificationService;
import ru.spbstu.taskmanager.service.TaskService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskService taskService;

    @BeforeEach
    void setup() {
        taskService.removeAllTasks();
    }

    @Test
    void getAllTasksShouldReturnTasks() {
        Task task = taskService.createTask("user1", "Test Task", LocalDate.now());

        ResponseEntity<Task[]> response = restTemplate.getForEntity("/users/user1/tasks", Task[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("Test Task", response.getBody()[0].getTitle());
    }

    @Test
    void getPendingTasksShouldReturnTasks() {
        Task task = taskService.createTask("user1", "Pending Task", LocalDate.now().plusDays(1));

        ResponseEntity<Task[]> response = restTemplate.getForEntity("/users/user1/tasks/pending", Task[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("Pending Task", response.getBody()[0].getTitle());
    }

    @Test
    void createTaskShouldReturnCreated() {
        CreateTaskRequest request = new CreateTaskRequest("New Task", LocalDate.now().plusDays(1));

        ResponseEntity<Task> response = restTemplate.postForEntity("/users/user1/tasks", request, Task.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("New Task", response.getBody().getTitle());
    }

    @Test
    void deleteTaskShouldReturnNoContent() {
        Task task = taskService.createTask("user1", "To Delete", LocalDate.now());

        ResponseEntity<Void> response = restTemplate.exchange(
                "/users/user1/tasks/" + task.getId(),
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

