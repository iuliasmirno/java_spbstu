package ru.spbstu.taskmanager.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.repository.TaskRepository;
import ru.spbstu.taskmanager.service.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Profile({"inmemory", "jpa"})
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${task.exchange}")
    private String exchange;

    @Value("${task.routing-key}")
    private String routingKey;

    public TaskServiceImpl(TaskRepository repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
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
        Task savedTask = repository.save(task);

        evictCache(userId);

        rabbitTemplate.convertAndSend(exchange, routingKey, savedTask);

        return savedTask;
    }

    @Override
    @Cacheable(value = "tasks", key = "#userId")
    public List<Task> getAllTasks(String userId) {
        return repository.findByUserId(userId).stream()
                .filter(t -> !t.isDeleted())
                .toList();
    }

    @Override
    @Cacheable(value = "pendingTasks", key = "#userId")
    public List<Task> getPendingTasks(String userId) {
        return repository.findByUserId(userId).stream()
                .filter(t -> !t.isDeleted() && !t.isCompleted())
                .toList();
    }

    @Override
    public boolean deleteTask(String userId, UUID id) {
        repository.markDeleted(userId, id);

        evictCache(userId);

        return true;
    }

    @Override
    public void removeAllTasks() {
        repository.removeAll();
    }

    @CacheEvict(value = {"tasks", "pendingTasks"}, key = "#userId")
    public void evictCache(String userId) {
        // Метод для ручного удаления кэша
    }
}
