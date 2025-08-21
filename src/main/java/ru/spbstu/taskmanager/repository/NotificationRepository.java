package ru.spbstu.taskmanager.repository;

import ru.spbstu.taskmanager.model.Notification;

import java.util.List;

public interface NotificationRepository {
    Notification save(Notification notification);
    <S extends Notification> Iterable<S> saveAll(Iterable<S> entities);
    List<Notification> findAllByUserId(String userId);
    List<Notification> findPendingByUser(String userId);

    boolean removeAll();
}
