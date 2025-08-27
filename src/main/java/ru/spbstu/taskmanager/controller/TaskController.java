package ru.spbstu.taskmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.taskmanager.dto.CreateTaskRequest;
import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.service.NotificationService;
import ru.spbstu.taskmanager.service.TaskService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/{userId}/tasks")
public class TaskController {
    private final TaskService service;
    private final NotificationService notificationService;

    public TaskController(TaskService service, NotificationService notificationService) {
        this.service = service;
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@PathVariable String userId) {
        List<Task> tasks = service.getAllTasks(userId);
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Task>> getPendingTasks(@PathVariable String userId) {
        List<Task> pendingTasks = service.getPendingTasks(userId);
        if (pendingTasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pendingTasks);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@PathVariable String userId,
                                           @RequestBody CreateTaskRequest request) {
        Task task = service.createTask(userId, request.title(), request.targetDate());
        notificationService.createNotification(userId, "New task created: " + request.title());
        return ResponseEntity.created(URI.create(task.getId().toString())).body(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String userId, @PathVariable UUID id) {
        if (!service.deleteTask(userId, id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
