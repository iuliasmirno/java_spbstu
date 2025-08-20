package ru.spbstu.taskmanager.repository;

import ru.spbstu.taskmanager.model.Notification;

import java.util.List;

public interface NotificationRepository {
    Notification save(Notification notification);
    List<Notification> findAllByUser(String userId);
    List<Notification> findPendingByUser(String userId);
}
