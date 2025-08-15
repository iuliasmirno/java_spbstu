package ru.spbstu.taskmanager.repository.impl;

import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.repository.TaskRepository;

import java.util.List;

public class InMemoryTaskRepository implements TaskRepository {
    @Override
    public Task save(Task task) {
        return null;
    }

    @Override
    public List<Task> findAll() {
        return List.of();
    }

    @Override
    public void markDeleted(String id) {

    }
}
