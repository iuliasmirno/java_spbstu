package ru.spbstu.taskmanager.service.impl;

import org.springframework.stereotype.Service;
import ru.spbstu.taskmanager.model.Notification;
import ru.spbstu.taskmanager.repository.NotificationRepository;
import ru.spbstu.taskmanager.service.NotificationService;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository repository;

    public NotificationServiceImpl(NotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Notification createNotification(String userId, String message) {
        return repository.save(new Notification(userId, message));
    }

    @Override
    public List<Notification> getAllNotifications(String userId) {
        return repository.findAllByUser(userId);
    }

    @Override
    public List<Notification> getPendingNotifications(String userId) {
        List<Notification> pending = repository.findPendingByUser(userId);
        pending.forEach(n -> n.setRead(true));
        return pending;
    }
}