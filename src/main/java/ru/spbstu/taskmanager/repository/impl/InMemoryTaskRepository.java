package ru.spbstu.taskmanager.repository.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.repository.TaskRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("inmemory")
public class InMemoryTaskRepository implements TaskRepository {
    private final Map<String, Task> storage = new ConcurrentHashMap<>();

    @Override
    public Task save(Task task) {
        storage.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> findByUserId(String userId) {
        return storage.values().stream()
                .filter(task -> task.getUserId().equals(userId))
                .toList();
    }

    @Override
    public void markDeleted(String userId, String id) {
        Task task = storage.get(id);
        if (task != null && task.getUserId().equals(userId)) {
            task.setDeleted(true);
        }
    }

    @Override
    public void removeAll() {
        storage.clear();
    }
}
