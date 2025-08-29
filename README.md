# MovieManager - Spring Boot Movie Management API

A simple **Movie Management REST API** built with **Spring Boot** and **MongoDB**, supporting CRUD operations with pagination, validation, and Swagger/OpenAPI documentation.

---

## Features

- **GET /movies** — List movies (paginated)
- **GET /movies/all** — List all movies
- **GET /movies/{id}** — Get a movie by ID
- **POST /movies** — Create a new movie
- **PUT /movies/{id}** — Update an existing movie
- **DELETE /movies/{id}** — Delete a movie by ID
- **Validation** — Ensures required fields are present
- **Exception Handling** — Returns clear error responses
- **Swagger UI** — API documentation at `/swagger-ui.html`

---

## Tech Stack

- Java 17
- Spring Boot 3.5
- Spring Data MongoDB
- MongoDB
- Springdoc OpenAPI (Swagger)
- Lombok
- Maven

---

## Setup Instructions

### 1. Clone the repository
```bash
git clone <repository-url>
cd moviemanager
