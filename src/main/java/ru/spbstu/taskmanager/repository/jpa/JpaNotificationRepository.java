package ru.spbstu.taskmanager.repository.jpa;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spbstu.taskmanager.model.Notification;
import ru.spbstu.taskmanager.repository.NotificationRepository;

import java.util.List;

@Repository
@Profile("jpa")
public interface JpaNotificationRepository extends NotificationRepository, JpaRepository<Notification, String> {
    List<Notification> findByUserIdOrderByCreationDateAsc(String userId);
    List<Notification> findByUserIdAndReadFalseOrderByCreationDateAsc(String userId);
    @Override
    @Query("select n from Notification n where n.userId = :userId and n.read = false order by n.creationDate asc")
    List<Notification> findPendingByUser(@Param("userId") String userId);

    @Override
    @Modifying
    @Transactional
    @Query("delete from Notification")
    void removeAll();
}
