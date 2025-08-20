package ru.spbstu.taskmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.taskmanager.model.Notification;
import ru.spbstu.taskmanager.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications(@RequestParam String userId) {
        List<Notification> notifications = service.getAllNotifications(userId);
        if (notifications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Notification>> getPendingNotifications(@RequestParam String userId) {
        List<Notification> pending = service.getPendingNotifications(userId);
        if (pending.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pending);
    }
}
