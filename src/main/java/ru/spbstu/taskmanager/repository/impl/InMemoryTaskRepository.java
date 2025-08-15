package ru.spbstu.taskmanager.repository.impl;

import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTaskRepository implements TaskRepository {
    private final Map<String, Task> storage = new ConcurrentHashMap<>();

    @Override
    public Task save(Task task) {
        storage.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void markDeleted(String id) {
        Task task = storage.get(id);
        if (task != null) {
            task.setDeleted(true);
        }
    }
}
