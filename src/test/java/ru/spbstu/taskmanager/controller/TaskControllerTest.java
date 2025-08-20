package ru.spbstu.taskmanager.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.service.TaskService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService service;

    @Test
    void getAllTasksShouldReturnOk() throws Exception {
        Task task = new Task("user1", "Test Task", LocalDate.now());
        when(service.getAllTasks("user1")).thenReturn(List.of(task));

        mockMvc.perform(get("/tasks/user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"));
    }

    @Test
    void createTaskShouldReturnCreated() throws Exception {
        LocalDate date = LocalDate.now();
        Task task = new Task("user1", "New Task", date);
        when(service.createTask(eq("user1"), eq("New Task"), eq(date))).thenReturn(task);

        mockMvc.perform(post("/tasks?userId=user1")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("New Task"))
                .andExpect(status().isCreated());
    }
}

