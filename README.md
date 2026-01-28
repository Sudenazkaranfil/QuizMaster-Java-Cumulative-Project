# QuizMaster - Comprehensive Quiz Management System

A full-stack quiz management application demonstrating **8 core software engineering concepts**: OOP, Interfaces, Generics, File Handling, JavaFX, Database Persistence, Web Interface, and Concurrency.

---

## Features

### Desktop Application (JavaFX)
- Create and manage quizzes with questions and answers
- Multiple difficulty levels (Easy, Medium, Hard)
- Export/Import quizzes as JSON
- Intuitive graphical user interface

### Web Application (Spring Boot + Thymeleaf)
- Browse available quizzes
- Take quizzes online
- View results and scores
- RESTful web interface

### Technical Highlights
- **OOP Principles**: Encapsulation, Inheritance, Polymorphism, Abstraction
- **Interface-based Design**: Clean separation of contracts and implementations
- **Generics**: Type-safe collections with `QuizDataContainer<T>` and `Pair<K,V>`
- **File Handling**: JSON serialization/deserialization with Jackson
- **Database**: Spring Data JPA with Hibernate ORM
- **Concurrency**: Multi-threaded quiz loading with ExecutorService
- **Web MVC**: Thymeleaf templates with Spring MVC

---

## Technologies

- **Backend**: Java 17, Spring Boot 3.x
- **Desktop UI**: JavaFX 21
- **Web UI**: Thymeleaf
- **Database**: H2 (in-memory), JPA/Hibernate
- **Build Tool**: Maven
- **File Format**: JSON (Jackson)

---

## Project Structure
```
QuizMaster/
├── src/main/java/com/quizmaster/
│   ├── model/              # Domain entities (Quiz, Question, Answer)
│   ├── repository/         # Spring Data JPA repositories
│   ├── service/            # Business logic layer
│   ├── controller/         # Web MVC controllers
│   ├── desktop/            # JavaFX application
│   ├── concurrency/        # Multi-threading implementation
│   └── util/               # Generic utilities, JSON serialization
├── src/main/resources/
│   ├── templates/          # Thymeleaf HTML templates
│   └── application.properties
└── pom.xml
```

---

## Running the Application

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Desktop Application
```bash
mvn javafx:run
```

### Web Application
```bash
mvn spring-boot:run
```
Then open: `http://localhost:8080/web/quizzes`

### H2 Database Console
```bash
http://localhost:8080/h2-console
```
- JDBC URL: `jdbc:h2:mem:quizdb`
- Username: `sa`
- Password: (leave empty)

---

## Core Concepts Demonstrated

### 1. OOP & Interfaces
- `Quiz`, `Question`, `Answer` interfaces define contracts
- `QuizEntity`, `QuizQuestion`, `QuizAnswer` implement with encapsulation
- Bidirectional relationships with helper methods
- Composition with cascade operations

### 2. Generics
- `QuizDataContainer<T>` - type-safe collection
- `Pair<K,V>` - generic key-value pairs
- Compile-time type safety

### 3. File Handling
- JSON serialization with Jackson ObjectMapper
- Export/Import quiz data
- Clean file I/O with Java NIO

### 4. JavaFX
- Desktop GUI with Scene switching
- VBox, GridPane, BorderPane layouts
- Event handling with lambda expressions

### 5. Database (JPA/Hibernate)
- Entity mapping with annotations
- Spring Data JPA repositories
- Automatic CRUD operations
- Derived query methods

### 6. Thymeleaf Web UI
- MVC pattern with Spring
- Server-side rendering
- Dynamic content with Thymeleaf expressions

### 7. Concurrency
- ExecutorService thread pool
- Parallel quiz loading
- Performance: Sequential (10s) vs Concurrent (2.5s)

---

## Academic Context

This project was developed as part of coursework demonstrating comprehensive Java development skills during an Erasmus exchange program.
