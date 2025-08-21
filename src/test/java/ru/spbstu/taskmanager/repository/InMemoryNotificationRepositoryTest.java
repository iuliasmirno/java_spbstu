package ru.spbstu.taskmanager.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.spbstu.taskmanager.model.Notification;
import ru.spbstu.taskmanager.repository.impl.InMemoryNotificationRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryNotificationRepositoryTest {

    private InMemoryNotificationRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryNotificationRepository();
    }

    @Test
    void saveShouldStoreNotification() {
        Notification n = new Notification("user1", "Hello!");
        repository.save(n);

        List<Notification> all = repository.findAllByUserId("user1");
        assertEquals(1, all.size());
        assertEquals("Hello!", all.get(0).getMessage());
    }

    @Test
    void findAllByUserShouldReturnSortedByDateId() {
        Notification n1 = new Notification("user1", "First");
        Notification n2 = new Notification("user1", "Second");
        repository.save(n2);
        repository.save(n1);

        List<Notification> all = repository.findAllByUserId("user1");

        assertEquals("First", all.get(0).getMessage()); // потому что по дате
    }

    @Test
    void findPendingByUserShouldReturnOnlyUnread() {
        Notification n1 = new Notification("user1", "First");
        Notification n2 = new Notification("user1", "Second");
        n2.setRead(true);
        repository.save(n1);
        repository.save(n2);

        List<Notification> pending = repository.findPendingByUser("user1");

        assertEquals(1, pending.size());
        assertEquals("First", pending.get(0).getMessage());
    }

    @Test
    void findPendingByUserShouldReturnEmptyIfNone() {
        Notification n = new Notification("user1", "Done");
        n.setRead(true);
        repository.save(n);

        List<Notification> pending = repository.findPendingByUser("user1");

        assertTrue(pending.isEmpty());
    }
}

