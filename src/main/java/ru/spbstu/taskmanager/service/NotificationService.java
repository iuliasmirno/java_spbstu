package ru.spbstu.taskmanager.service;

import ru.spbstu.taskmanager.model.Notification;

import java.util.List;

public interface NotificationService {
//    Notification createNotification(String userId, String message);
    List<Notification> getAllNotifications(String userId);
    List<Notification> getPendingNotifications(String userId);

    void deleteAllNotifications();
}
