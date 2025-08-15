package ru.spbstu.taskmanager.model;

import java.time.LocalDate;
import java.util.UUID;

public class Task {
    private final String id;
    private final String userId;
    private final LocalDate creationDate;
    private String title;
    private boolean completed;
    private boolean deleted;
    private LocalDate targetDate;

    public Task(String userId, String title, LocalDate targetDate) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.title = title;
        this.completed = false;
        this.deleted = false;
        this.creationDate = LocalDate.now();
        this.targetDate = targetDate;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
    public LocalDate getCreationDate() { return creationDate; }
    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }
}