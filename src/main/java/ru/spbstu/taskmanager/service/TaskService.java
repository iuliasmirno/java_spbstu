package ru.spbstu.taskmanager.service;

import ru.spbstu.taskmanager.model.Task;
import java.util.List;

public interface TaskService {
    Task createTask(String userId, String title);
    List<Task> getAllTasks(String userId);
    List<Task> getPendingTasks(String userId);
    boolean deleteTask(String userId, String id);
}