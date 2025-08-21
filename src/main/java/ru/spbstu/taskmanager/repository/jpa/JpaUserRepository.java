package ru.spbstu.taskmanager.repository.jpa;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.spbstu.taskmanager.model.User;
import ru.spbstu.taskmanager.repository.UserRepository;

@Repository
@Profile("jpa")
public interface JpaUserRepository extends UserRepository, JpaRepository<User, String> {
    User findByUsername(String username);

    @Override
    @Modifying
    @Transactional
    @Query("delete from User")
    void removeAll();
}
