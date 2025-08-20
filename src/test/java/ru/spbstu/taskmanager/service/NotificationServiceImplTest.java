package ru.spbstu.taskmanager.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.spbstu.taskmanager.model.Notification;
import ru.spbstu.taskmanager.repository.NotificationRepository;
import ru.spbstu.taskmanager.service.impl.NotificationServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository repository;

    @InjectMocks
    private NotificationServiceImpl service;

    @Test
    void createNotificationShouldSave() {
        Notification n = new Notification("user1", "Test");
        when(repository.save(any(Notification.class))).thenReturn(n);

        Notification created = service.createNotification("user1", "Test");

        assertEquals("Test", created.getMessage());
        assertEquals("user1", created.getUserId());
        verify(repository).save(any(Notification.class));
    }

    @Test
    void getAllNotificationsShouldReturnAll() {
        Notification n1 = new Notification("user1", "Msg1");
        Notification n2 = new Notification("user1", "Msg2");
        when(repository.findAllByUser("user1")).thenReturn(List.of(n1, n2));

        List<Notification> all = service.getAllNotifications("user1");

        assertEquals(2, all.size());
        assertTrue(all.contains(n1));
        assertTrue(all.contains(n2));
    }

    @Test
    void getPendingNotificationsShouldReturnAndMarkRead() {
        Notification n1 = new Notification("user1", "Msg1");
        Notification n2 = new Notification("user1", "Msg2");
        n1.setRead(false);
        n2.setRead(false);

        when(repository.findPendingByUser("user1")).thenReturn(List.of(n1, n2));

        List<Notification> pending = service.getPendingNotifications("user1");

        assertEquals(2, pending.size());
        assertTrue(pending.get(0).isRead());
        assertTrue(pending.get(1).isRead());

        verify(repository).findPendingByUser("user1");
    }

    @Test
    void getPendingNotificationsShouldReturnEmptyIfNone() {
        when(repository.findPendingByUser("user1")).thenReturn(List.of());

        List<Notification> pending = service.getPendingNotifications("user1");

        assertTrue(pending.isEmpty());
        verify(repository).findPendingByUser("user1");
    }
}

