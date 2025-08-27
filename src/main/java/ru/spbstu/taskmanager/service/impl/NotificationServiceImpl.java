package ru.spbstu.taskmanager.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.spbstu.taskmanager.model.Notification;
import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.repository.NotificationRepository;
import ru.spbstu.taskmanager.service.NotificationService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Profile({"inmemory", "jpa"})
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository repository;

    public NotificationServiceImpl(NotificationRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "${task.queue}")
    public Notification handleTaskCreated(Task task) {
        Notification notification = new Notification();
        notification.setUserId(task.getUserId());
        notification.setMessage("New task created: " + task.getTitle());
        notification.setCreationDate(LocalDateTime.now());
        notification.setRead(false);
        return repository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications(String userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public List<Notification> getPendingNotifications(String userId) {
        List<Notification> pending = repository.findPendingByUser(userId);
        pending.forEach(n -> n.setRead(true));
        repository.saveAll(pending);
        return pending;
    }

    @Override
    public void deleteAllNotifications() {
        repository.removeAll();
    }
}
