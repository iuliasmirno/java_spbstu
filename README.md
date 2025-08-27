# Step 6: Implement Caching (Redis)

## Overview
This step involved introducing caching functionality into the application using Redis as the caching provider alongside Spring Cache abstraction. The purpose was to enhance performance by minimizing frequent database accesses and serving repeated requests faster.

---


## Functional Description
The caching mechanism operates on a straightforward principle:
1. When a request arrives seeking certain data (such as list of tasks):
    - First, the application searches for the requested item in Redis cache.
    - If found, it returns the cached result instantly.
    - If missing, it retrieves the data from the underlying PostgreSQL database, stores it in Redis with predefined timeout, and finally delivers it back to the caller.

2. Cached entries expire automatically after reaching their designated lifespan (defined in `application.properties`), ensuring that outdated information does not persist indefinitely.

---

This concludes the detailed summary of modifications made during Step 6 regarding Redis caching implementation.