package ru.spbstu.taskmanager.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.spbstu.taskmanager.model.Notification;
import ru.spbstu.taskmanager.service.NotificationService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationService notificationService;

    @Test
    void getAllNotificationsShouldReturnOk() throws Exception {
        Notification n1 = new Notification("user1", "Notification 1");
        when(notificationService.getAllNotifications("user1")).thenReturn(List.of(n1));

        mockMvc.perform(get("/notifications")
                        .param("userId", "user1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message").value("Notification 1"));
    }

    @Test
    void getAllNotificationsShouldReturnNoContent() throws Exception {
        when(notificationService.getAllNotifications("user1")).thenReturn(List.of());

        mockMvc.perform(get("/notifications")
                        .param("userId", "user1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getPendingNotificationsShouldReturnOk() throws Exception {
        Notification n1 = new Notification("user1", "Pending Notification");
        when(notificationService.getPendingNotifications("user1")).thenReturn(List.of(n1));

        mockMvc.perform(get("/notifications/pending")
                        .param("userId", "user1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message").value("Pending Notification"));
    }

    @Test
    void getPendingNotificationsShouldReturnNoContent() throws Exception {
        when(notificationService.getPendingNotifications("user1")).thenReturn(List.of());

        mockMvc.perform(get("/notifications/pending")
                        .param("userId", "user1"))
                .andExpect(status().isNoContent());
    }
}
