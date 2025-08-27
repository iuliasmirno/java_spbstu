package ru.spbstu.taskmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.spbstu.taskmanager.model.Notification;
import ru.spbstu.taskmanager.service.NotificationService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationService notificationService;

    private final String userId = "user1";

    @Test
    void getAllNotificationsShouldReturnNotifications() throws Exception {
        UUID id1 = UUID.randomUUID();
        Notification n1 = new Notification(userId, "Test notification 1");
        n1.setId(id1);

        UUID id2 = UUID.randomUUID();
        Notification n2 = new Notification(userId, "Test notification 2");
        n2.setId(id2);

        Mockito.when(notificationService.getAllNotifications(userId))
                .thenReturn(List.of(n1, n2));

        mockMvc.perform(get("/notifications")
                        .param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message").value("Test notification 1"))
                .andExpect(jsonPath("$[1].message").value("Test notification 2"))
                .andExpect(jsonPath("$[0].id").value(id1.toString()))
                .andExpect(jsonPath("$[1].id").value(id2.toString()));
    }

    @Test
    void getAllNotificationsShouldReturnNoContentIfEmpty() throws Exception {
        Mockito.when(notificationService.getAllNotifications(userId))
                .thenReturn(List.of());

        mockMvc.perform(get("/notifications")
                        .param("userId", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    void getPendingNotificationsShouldReturnPending() throws Exception {
        Notification n1 = new Notification(userId, "Pending notification 1");
//        n1.setId(3L);
        n1.setRead(true); // допустим pending = isRead == true
        Notification n2 = new Notification(userId, "Pending notification 2");
//        n2.setId(4L);
        n2.setRead(true);

        Mockito.when(notificationService.getPendingNotifications(userId))
                .thenReturn(List.of(n1, n2));

        mockMvc.perform(get("/notifications/pending")
                        .param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message").value("Pending notification 1"))
                .andExpect(jsonPath("$[1].message").value("Pending notification 2"));
    }

    @Test
    void getPendingNotificationsShouldReturnNoContentIfNone() throws Exception {
        Mockito.when(notificationService.getPendingNotifications(anyString()))
                .thenReturn(List.of());

        mockMvc.perform(get("/notifications/pending")
                        .param("userId", userId))
                .andExpect(status().isNoContent());
    }
}
