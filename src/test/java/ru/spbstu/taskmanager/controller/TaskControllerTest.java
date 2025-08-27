package ru.spbstu.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    private static final Logger log = LoggerFactory.getLogger(TaskControllerTest.class);
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private NotificationService notificationService;

    @Test
    void getAllTasksShouldReturnTasks() throws Exception {
        Task task = new Task("user1", "Test Task", LocalDate.now());
//        task.setId(1L);

        Mockito.when(taskService.getAllTasks("user1"))
                .thenReturn(List.of(task));

        mockMvc.perform(get("/users/user1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"))
                .andExpect(jsonPath("$[0].userId").value("user1"));
    }

    @Test
    void getPendingTasksShouldReturnTasks() throws Exception {
        Task task = new Task("user1", "Pending Task", LocalDate.now().plusDays(1));
//        task.setId(2L);

        Mockito.when(taskService.getPendingTasks("user1"))
                .thenReturn(List.of(task));

        mockMvc.perform(get("/users/user1/tasks/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Pending Task"));
    }

    @Test
    void createTaskShouldReturnCreated() throws Exception {
        Task testTask = new Task();
        testTask.setUserId("user1");
        testTask.setTitle("New Task");
        testTask.setTargetDate(LocalDate.now().plusDays(1));
        testTask.setId(UUID.randomUUID());

        Mockito.when(taskService.createTask(eq("user1"), eq("New Task"), any(LocalDate.class)))
                .thenReturn(testTask);

        String requestJson = """
            {
              "title": "New Task",
              "targetDate": "%s"
            }
            """.formatted(LocalDate.now().plusDays(1));

        mockMvc.perform(post("/users/user1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Task"))
                .andExpect(jsonPath("$.userId").value("user1"));
    }


    @Test
    void deleteTaskShouldReturnNoContent() throws Exception {
        UUID taskId = UUID.randomUUID();

        when(taskService.deleteTask("user1", taskId)).thenReturn(true);

        mockMvc.perform(delete("/users/user1/tasks/" + taskId))
                .andExpect(status().isNoContent());
    }
}

