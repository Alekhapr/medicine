# Medicine Store Inventory Management System

A full-stack web-based inventory management system for a medicine store.

## Tech Stack
- **Backend:** Java Spring Boot (layered architecture)
- **Database:** MySQL + JPA/Hibernate
- **Frontend:** Thymeleaf + HTML/CSS/JavaScript
- **Build Tool:** Maven

## Folder Structure

```text
src/main/java/com/medicinestore/inventory
├── controller
├── dto
├── entity
├── exception
├── repository
└── service

src/main/resources
├── static/css
├── static/js
├── templates
├── application.properties
└── data.sql
```

## Run
1. Update MySQL credentials in `application.properties`.
2. Create DB (or let Spring auto-create with `createDatabaseIfNotExist=true`).
3. Run:
   ```bash
   mvn spring-boot:run
   ```
4. Open: `http://localhost:8080`

## Notes
- `data.sql` contains sample insert data.
- Dashboard shows total medicines, total stock, expired and low-stock counters.
- Expired medicines are auto-detected based on `expiryDate < current_date`.
