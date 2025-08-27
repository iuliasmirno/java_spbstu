# Step 5: Migration to PostgreSQL Database

## Overview
This step represents a significant architectural improvement by migrating our application from the embedded H2 database to the robust PostgreSQL relational database management system. PostgreSQL provides better performance, scalability, and enterprise-level features compared to the in-memory H2 solution previously used.

---

## Key Changes Implemented

### 1. Replaced H2 with PostgreSQL
The application has been updated to use PostgreSQL as its primary database engine. This change enhances data persistence, transactional integrity, and supports larger-scale deployments.

### 2. Updated `application.properties`
The configuration properties were modified to establish connections to the PostgreSQL database instance. New parameters include:
- Connection URL
- Username
- Password
- Driver class name

### 3. Integrated Flyway for Database Migrations
Flyway was incorporated to manage schema versions and apply incremental changes automatically upon application startup. This ensures smooth upgrades and rollbacks of database structures.
