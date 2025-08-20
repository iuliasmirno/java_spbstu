package ru.spbstu.taskmanager.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {
    private final String id;
    private final String userId;
    private final String message;
    private final LocalDateTime creationDate;
    private boolean read;

    public Notification(String userId, String message) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.message = message;
        this.creationDate = LocalDateTime.now();
        this.read = false;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getMessage() { return message; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
}
