package ru.spbstu.taskmanager.repository;

import ru.spbstu.taskmanager.model.Task;
import java.util.List;

public interface TaskRepository {
    Task save(Task task);
    List<Task> findAll();
    boolean markDeleted(String id);
}
