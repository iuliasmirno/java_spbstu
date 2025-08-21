package ru.spbstu.taskmanager.repository.impl;

import org.springframework.stereotype.Repository;
import ru.spbstu.taskmanager.model.Notification;
import ru.spbstu.taskmanager.repository.NotificationRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryNotificationRepository implements NotificationRepository {
    private final Map<String, Notification> storage = new ConcurrentHashMap<>();

    @Override
    public Notification save(Notification notification) {
        storage.put(notification.getId(), notification);
        return notification;
    }

    @Override
    public List<Notification> findAllByUser(String userId) {
        return storage.values().stream()
                .filter(n -> n.getUserId().equals(userId))
                .sorted(Comparator.comparing(Notification::getCreationDate))
                .toList();
    }

    @Override
    public List<Notification> findPendingByUser(String userId) {
        return storage.values().stream()
                .filter(n -> n.getUserId().equals(userId) && !n.isRead())
                .sorted(Comparator.comparing(Notification::getCreationDate))
                .toList();
    }

    @Override
    public boolean removeAll() {
        storage.clear();
        return true;
    }
}
