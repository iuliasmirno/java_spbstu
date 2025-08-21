package ru.spbstu.taskmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    private String userId = "user1";

    @BeforeEach
    void setup() {
        notificationService.deleteAllNotifications();
    }

    @Test
    void getAllNotificationsShouldReturnNotifications() {
        // создаём уведомления
        Notification n1 = notificationService.createNotification(userId, "Test notification 1");
        Notification n2 = notificationService.createNotification(userId, "Test notification 2");

        ResponseEntity<Notification[]> response =
                restTemplate.getForEntity("/notifications?userId=" + userId, Notification[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<Notification> notifications = Arrays.asList(response.getBody());
        assertTrue(notifications.stream().anyMatch(n -> n.getMessage().equals("Test notification 1")));
        assertTrue(notifications.stream().anyMatch(n -> n.getMessage().equals("Test notification 2")));
    }

    @Test
    void getAllNotificationsShouldReturnNoContentIfEmpty() {
        ResponseEntity<Notification[]> response =
                restTemplate.getForEntity("/notifications?userId=" + userId, Notification[].class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getPendingNotificationsShouldReturnPending() {
        // создаём уведомления — они уже pending
        Notification n1 = notificationService.createNotification(userId, "Pending notification 1");
        Notification n2 = notificationService.createNotification(userId, "Pending notification 2");

        ResponseEntity<Notification[]> response =
                restTemplate.getForEntity("/notifications/pending?userId=" + userId, Notification[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<Notification> pending = Arrays.asList(response.getBody());
        assertEquals(2, pending.size());
        assertTrue(pending.stream().allMatch(n -> n.isRead()));
    }

    @Test
    void getPendingNotificationsShouldReturnNoContentIfNone() {
        ResponseEntity<Notification[]> response =
                restTemplate.getForEntity("/notifications/pending?userId=" + userId, Notification[].class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
