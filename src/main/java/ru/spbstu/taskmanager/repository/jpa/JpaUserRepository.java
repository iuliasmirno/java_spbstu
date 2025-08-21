package ru.spbstu.taskmanager.repository.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.taskmanager.model.User;
import ru.spbstu.taskmanager.repository.UserRepository;

@Repository
@Profile("jpa")
public interface JpaUserRepository extends UserRepository, JpaRepository<User, String> {
    User findByUsername(String username);
}
