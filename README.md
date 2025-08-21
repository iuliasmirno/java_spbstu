# Step 4: Add Docker Support

## Overview
This step focuses on containerizing our Spring Boot application using Docker technology. Containerization simplifies deployment processes, ensures consistency across environments, and allows easy scaling of applications.

---

## Objectives
- Create a Dockerfile to package the Spring Boot application into a lightweight container image.
- Configure Docker Compose to manage both the application and its dependent database services simultaneously.
- Validate the application's functionality within a containerized environment.

---

## Technical Implementation
Key points:
- Uses slim OpenJDK 23 image for minimal footprint.
- Sets working directory inside the container.
- Copies built artifact (`taskmanager-0.0.1-SNAPSHOT.jar`) renamed to `app.jar`.
- Exposes port 8080 for communication.
- Defines entrypoint command to run the jar directly.

---


Configuration highlights:
- Builds image from current directory context.
- Names container explicitly for clarity.
- Configures Spring profile activation and datasource settings.
- Maps host port 8080 to container internal port 8080.

---

## Usage Instructions

### Building Image
Run the following command at project root level:
```bash
docker compose build
```

### Starting Application
Start the application along with its database dependency:
```bash
docker compose up
```

### Stopping Application
Gracefully stop all related containers:
```bash
docker compose down
```

