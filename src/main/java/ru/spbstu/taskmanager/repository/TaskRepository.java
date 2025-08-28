package ru.spbstu.taskmanager.repository;

import ru.spbstu.taskmanager.model.Task;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface TaskRepository {
    Task save(Task task);
    List<Task> findByUserId(String userId);
    void markDeleted(String userId, UUID id);

    void removeAll();

    List<Task> findAll();
}
