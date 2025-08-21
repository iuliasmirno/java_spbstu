package ru.spbstu.taskmanager.repository.jpa;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spbstu.taskmanager.model.Task;
import ru.spbstu.taskmanager.repository.TaskRepository;

import java.util.List;

@Repository
@Profile("jpa")
public interface JpaTaskRepository extends TaskRepository, JpaRepository<Task, String> {
    List<Task> findByUserId(String userId);

    @Transactional
    @Modifying
    @Query("update Task t set t.deleted = true where t.userId = :userId and t.id = :id")
    void markDeleted(@Param("userId") String userId, @Param("id") String id);

    @Override
    @Modifying
    @Transactional
    @Query("delete from Task")
    void removeAll();
}
