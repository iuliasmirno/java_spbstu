package ru.spbstu.taskmanager.service.impl;

import org.springframework.stereotype.Service;
import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.repository.TaskRepository;
import ru.spbstu.taskmanager.service.TaskService;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task createTask(String title) {
        return repository.save(new Task(title));
    }

    @Override
    public List<Task> getAllTasks() {
        return repository.findAll().stream()
                .filter(t -> !t.isDeleted())
                .toList();
    }

    @Override
    public List<Task> getPendingTasks() {
        return repository.findAll().stream()
                .filter(t -> !t.isDeleted() && !t.isCompleted())
                .toList();
    }

    @Override
    public boolean deleteTask(String id) {
        return repository.markDeleted(id);
    }
}
