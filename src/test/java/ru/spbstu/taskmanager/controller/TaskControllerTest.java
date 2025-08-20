package ru.spbstu.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.spbstu.taskmanager.dto.CreateTaskRequest;
import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.service.NotificationService;
import ru.spbstu.taskmanager.service.TaskService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService service;

    @MockitoBean
    private NotificationService notificationService;

    @Test
    void getAllTasksShouldReturnOk() throws Exception {
        Task task = new Task("user1", "Test Task", LocalDate.now());
        when(service.getAllTasks("user1")).thenReturn(List.of(task));

        mockMvc.perform(get("/users/user1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"));
    }

    @Test
    void getPendingTasksShouldReturnOk() throws Exception {
        Task task = new Task("user1", "Pending Task", LocalDate.now());
        when(service.getPendingTasks("user1")).thenReturn(List.of(task));

        mockMvc.perform(get("/users/user1/tasks/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Pending Task"));
    }

    @Test
    void createTaskShouldReturnCreated() throws Exception {
        LocalDate targetDate = LocalDate.now().plusDays(1);
        Task task = new Task("user1", "New Task", targetDate);

        // мокаем сервис
        when(service.createTask(eq("user1"), eq("New Task"), eq(targetDate))).thenReturn(task);

        // создаём JSON для запроса
        String json = objectMapper.writeValueAsString(new CreateTaskRequest("New Task", targetDate));

        mockMvc.perform(post("/users/user1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    void deleteTaskShouldReturnNoContent() throws Exception {
        when(service.deleteTask("user1", "task1")).thenReturn(true);

        mockMvc.perform(delete("/users/user1/tasks/task1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTaskShouldReturnNotFound() throws Exception {
        when(service.deleteTask("user1", "task2")).thenReturn(false);

        mockMvc.perform(delete("/users/user1/tasks/task2"))
                .andExpect(status().isNotFound());
    }
}

