package ru.spbstu.taskmanager.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String userId;
    private LocalDate creationDate;
    private String title;
    private boolean completed;
    private boolean deleted;
    private LocalDate targetDate;

    public Task(String userId, String title, LocalDate targetDate) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.title = title;
        this.completed = false;
        this.deleted = false;
        this.creationDate = LocalDate.now();
        this.targetDate = targetDate;
    }

    public Task() {

    }

    public UUID getId() { return id; }
    public String getUserId() { return userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public void setUserId(String userId) { this.userId = userId; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
    public LocalDate getCreationDate() { return creationDate; }
    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }
}