# Subscription And Tier Management Service

This project is a backend service built using Spring Boot that exposes REST APIs to manage subscription and tier management.  
It follows a layered architecture and demonstrates clean separation of concerns.

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- REST APIs

---

## Architecture

The project follows a layered structure:

Controller → Service → Repository → Database

- Controller layer handles HTTP requests/responses
- Service layer contains business logic
- Repository layer interacts with the database
- Global exception handling is implemented for error responses

---

## How to Run the Project

### Prerequisites

- Java 17 or above
- MySQL installed and running

### Steps

1. Clone the repository

   git clone <YOUR_GITHUB_REPO_LINK>

2. Navigate into the project directory

   cd <PROJECT_FOLDER_NAME>

3. Configure database credentials in:

   src/main/resources/application.properties

   Example:
   spring.datasource.url=jdbc:mysql://localhost:3306/<db_name>
   spring.datasource.username=<username>
   spring.datasource.password=<password>

4. Run the application

   ./mvnw spring-boot:run

The server will start at:

http://localhost:8080

---

## Assumptions

- IDs are auto-generated
- Input validation is handled at the API layer
- Database schema is auto-created using JPA

---

## Error Handling

- Global exception handling using `@ControllerAdvice`
- Meaningful HTTP status codes are returned

---

## Future Improvements

- Add authentication & authorization
- Add Docker support
- Add unit and integration tests
- Add API documentation using Swagger

---

## Author

Anand Kumar Singh
