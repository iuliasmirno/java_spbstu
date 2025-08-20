# Step 1: Basic REST API with In-Memory Storage

## Overview
This is the first step of our backend development task focused on creating a basic REST API using Java Spring framework. This initial stage involves setting up a Spring Boot project without any external databases, relying instead on in-memory storage mechanisms such as Lists or Maps.

---


## Project Setup

### Tools Used
- **Spring Boot** version 3.5.4 (generated via https://start.spring.io/)
- **Build Tool**: chose Maven
- **IDE**: IntelliJ IDEA

### Initial Configuration
Generate a base Spring Boot application including dependencies for Web, Test, and optionally Lombok.

---

## Implementation Details

### Controllers
#### TaskController
- `/tasks`: GET endpoint returning all user tasks.
- `/tasks/pending`: GET endpoint showing only pending tasks.
- `/tasks`: POST endpoint allowing task creation.
- `/tasks/{id}`: DELETE endpoint marking task as deleted (soft deletion logic implemented).

#### UserController
- `/users/login`: Simulated login functionality identifying users by credentials.
- `/users/register`: Endpoint handling user registrations.

#### NotificationController
- `/notifications`: GET endpoint listing all user's notifications.
- `/notifications/pending`: GET endpoint displaying only unread notifications.

### Services
Each Controller interact with corresponding Service layer implementing business logic.

### Repositories
Repositories handle CRUD operations over in-memory collections (List/Map).

---

## Data Structures
Task entities include fields such as:
- Creation Date
- Target Completion Date
- Status (Completed and deleted)

User entity includes authentication details and associated tasks.
Notification entity contains message content and read/unread state.

