package ru.spbstu.taskmanager.service;

import ru.spbstu.taskmanager.model.Task;
import java.util.List;

public interface TaskService {
    Task createTask(String title);
    List<Task> getAllTasks();
    List<Task> getPendingTasks();
    boolean deleteTask(String id);
}