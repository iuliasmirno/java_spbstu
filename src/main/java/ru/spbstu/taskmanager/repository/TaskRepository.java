package ru.spbstu.taskmanager.repository;

import ru.spbstu.taskmanager.model.Task;
import java.util.List;

public interface TaskRepository {
    Task save(Task task);
    List<Task> findByUserId(String userId);
    void markDeleted(String userId, String id);

    void removeAll();
}
