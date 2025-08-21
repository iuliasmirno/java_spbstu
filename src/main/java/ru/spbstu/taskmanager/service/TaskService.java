package ru.spbstu.taskmanager.service;

import ru.spbstu.taskmanager.model.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    Task createTask(String userId, String title, LocalDate targetDate);
    List<Task> getAllTasks(String userId);
    List<Task> getPendingTasks(String userId);
    boolean deleteTask(String userId, String id);

    void removeAllTasks();
}