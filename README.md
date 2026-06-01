# Workload Planner

Workload Planner is a web-based Scrum board application where a project manager can create and manage scrum boards and tickets, and members can assign themself to tickets and change their status. 
A manager can also assign members to tickets, as well as remove them or update their status.

## Project Context

This Workload Planner was created as a student project.

- Program: UCLL Programmeren
- Course: GIP5
- Academic Year: 2025–2026
- Teacher: Serhat Erdogan
- Student: Roxane Reza

## Live Demo
https://workload-planner-production-e85c.up.railway.app

## What it does

The Workload Planner allows teams to manage work through boards and tickets.
There are two user roles with different permissions:

**Project Manager:**
- Create, edit and delete boards
- Create, edit and delete tickets
- Assign members to tickets and remove assignments
- View analytics dashboard (ticket stats, board overview, assignments per member)

**Member:**
- View boards and tickets
- Assign themselves to tickets and remove their own assignment
- Change the status of tickets (TODO → IN PROGRESS → DONE)

---

## Technology

| Layer | Technology                        |
|---|-----------------------------------|
| Backend | Java Spring Boot 4                |
| Database | PostgreSQL 15                     |
| Frontend | Thymeleaf + Bootstrap 5 (via CDN) |
| Security | Spring Security                   |
| Dev environment | Docker                            |
| Deployment | Railway |

---

## How to run locally

### Prerequisites
- Java 21
- Docker Desktop
- IntelliJ IDEA

### Steps

1. Clone the repository:
```bash
git clone https://github.com/yourusername/workload-planner.git
```

2. Start the PostgreSQL database via Docker:
```bash
docker-compose up -d
```

3. Run the application in IntelliJ by clicking the green play button
   on `WorkloadPlannerApplication.java`

4. Visit `http://localhost:8081` in your browser

### Test accounts

| Username | Password | Role |
|---|---|---|
| manager | manager123 | Project Manager |
| member1 | member123 | Member |
| member2 | member123 | Member |

---

## Project structure

src/main/java/com/roxane/workload_planner/

    ├── config/         → Security config, data initializer, Custom User Details service

    ├── controller/     → Handle web requests

    ├── model/          → Database entities

    ├── repository/     → Database access

    └── service/        → Business logic

src/main/resources/
    
    ├── templates/      → Thymeleaf HTML pages

        │├── analytics/  → Analytics dashboard

        │└── auth/       → Login page

        │├── boards/     → Board list and forms

        │├── error.html  → Custom error page

        └── layout.html → Shared layout template

---

## Database schema

| Table | Description |
|---|---|
| users | Stores all users with their role |
| boards | Scrum boards created by managers |
| tickets | Tasks belonging to a board |
| ticket_assignments | Links members to tickets (many-to-many) |

---

## Features

- Role-based access control (Spring Security)
- Scrum board with three columns: TODO, IN PROGRESS, DONE
- Managers can assign/unassign members to tickets
- Members can assign/unassign themselves
- Analytics dashboard for managers
- Confirmation dialogs before deleting
- Success and error messages after actions
- Friendly error page (403, 404, 500)
- Test users created automatically on first startup

---

## Author

Roxane Reza