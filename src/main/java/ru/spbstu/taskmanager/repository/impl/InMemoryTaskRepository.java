package ru.spbstu.taskmanager.repository.impl;

import org.springframework.stereotype.Repository;
import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTaskRepository implements TaskRepository {
    private final Map<String, Task> storage = new ConcurrentHashMap<>();

    @Override
    public Task save(Task task) {
        storage.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> findAllByUser(String userId) {
        return storage.values().stream()
                .filter(task -> task.getUserId().equals(userId))
                .toList();
    }

    @Override
    public boolean markDeleted(String userId, String id) {
        Task task = storage.get(id);
        if (task != null && task.getUserId().equals(userId)) {
            task.setDeleted(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll() {
        storage.clear();
        return true;
    }
}
