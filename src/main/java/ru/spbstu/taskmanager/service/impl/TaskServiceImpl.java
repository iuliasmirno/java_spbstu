package ru.spbstu.taskmanager.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.repository.TaskRepository;
import ru.spbstu.taskmanager.service.TaskService;

import java.time.LocalDate;
import java.util.List;

@Service
@Profile({"inmemory", "jpa"})
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task createTask(String userId, String title, LocalDate targetDate) {
        Task task = new Task();
        task.setUserId(userId);
        task.setTitle(title);
        task.setTargetDate(targetDate);
        task.setCreationDate(LocalDate.now());
        task.setDeleted(false);
        task.setCompleted(false);
        return repository.save(task);
    }

    @Override
    public List<Task> getAllTasks(String userId) {
        return repository.findByUserId(userId).stream()
                .filter(t -> !t.isDeleted())
                .toList();
    }

    @Override
    public List<Task> getPendingTasks(String userId) {
        return repository.findByUserId(userId).stream()
                .filter(t -> !t.isDeleted() && !t.isCompleted())
                .toList();
    }

    @Override
    public boolean deleteTask(String userId, String id) {
        return repository.markDeleted(userId, id);
    }

    @Override
    public void removeAllTasks() {
        repository.removeAll();
    }
}
