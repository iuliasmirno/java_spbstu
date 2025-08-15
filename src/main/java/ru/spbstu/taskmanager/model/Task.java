package ru.spbstu.taskmanager.model;

import java.util.UUID;

public class Task {
    private final String id;
    private String title;
    private boolean completed;
    private boolean deleted;

    public Task(String title) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.completed = false;
        this.deleted = false;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}