package ru.spbstu.taskmanager.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String userId;
    private String message;
    private LocalDateTime creationDate;
    private boolean read;

    public Notification(String userId, String message) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.message = message;
        this.creationDate = LocalDateTime.now();
        this.read = false;
    }

    public Notification() {

    }

    public UUID getId() { return id; }
    public String getUserId() { return userId; }
    public String getMessage() { return message; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setMessage(String message) { this.message = message; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
}
