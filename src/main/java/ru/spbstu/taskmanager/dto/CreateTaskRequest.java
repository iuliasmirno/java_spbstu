package ru.spbstu.taskmanager.dto;

import java.time.LocalDate;

public record CreateTaskRequest(String title, LocalDate targetDate) {}
