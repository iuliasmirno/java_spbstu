package ru.spbstu.taskmanager.service.impl;

import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.service.TaskService;

import java.util.List;

public class TaskServiceImpl implements TaskService {
    @Override
    public Task createTask(String title) {
        return null;
    }

    @Override
    public List<Task> getAllTasks() {
        return List.of();
    }

    @Override
    public List<Task> getPendingTasks() {
        return List.of();
    }

    @Override
    public void deleteTask(String id) {

    }
}
