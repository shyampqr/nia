# AGENTS.md

## Project Overview
This is a Spring Boot web application for Nextgenz International Academy, structured as a classic MVC (Model-View-Controller) Java project. The application serves static and dynamic web pages using Thymeleaf templates and is designed for extensibility with a (currently commented-out) JPA-based persistence layer.

## Key Components
- **Controllers**: All HTTP endpoints are defined in `src/main/java/com/nextgenz/controller/`. Example: `HomeController.java` maps `/`, `/about`, and `/admission` to their respective views.
- **Entities & Repositories**: The `Student` entity and `StudentRepository` exist but are commented out. If enabled, they use JPA for persistence (see `entity/Student.java` and `repository/StudentRepository.java`).
- **Templates**: HTML views are in `src/main/resources/templates/` and use Thymeleaf. Example files: `index.html`, `about.html`, `admission.html`.
- **Configuration**: Application settings (including database connection) are in `src/main/resources/application.properties`.
- **Build**: Managed by Maven (`pom.xml`). Uses Java 17 and Spring Boot 3.2.5.

## Developer Workflows
- **Build & Run**: Use `mvn spring-boot:run` or build with `mvn package` and run the JAR in `target/`.
- **Database**: MySQL is configured in `application.properties`. JPA/Hibernate auto-DDL is set to `update`.
- **Hot Reload**: Not configured by default. Add Spring DevTools for live reload if needed.
- **Testing**: No test classes present. Add tests under `src/test/java/` following standard Maven conventions.

## Project-Specific Patterns & Conventions
- **Thymeleaf**: All HTML templates use Thymeleaf syntax for dynamic content. Example: `<html xmlns:th="http://www.thymeleaf.org">` in `index.html`.
- **Minimal Controller Logic**: Controllers currently only map routes to views; no business logic or service layer is present.
- **Commented Code**: Persistence (JPA, MySQL) is present but commented out in both code and `pom.xml`. Uncomment to enable full CRUD features.
- **No Custom Error Handling**: No global exception handlers or error pages are defined.

## Integration Points
- **Spring Boot**: Main entry point is `NextgenzApplication.java`.
- **MySQL**: Connection details in `application.properties`. Ensure MySQL is running locally for DB features.
- **Bootstrap**: UI uses Bootstrap via CDN in `index.html`.

## Example File References
- Controller: `src/main/java/com/nextgenz/controller/HomeController.java`
- Entity: `src/main/java/com/nextgenz/entity/Student.java` (commented)
- Repository: `src/main/java/com/nextgenz/repository/StudentRepository.java` (commented)
- Template: `src/main/resources/templates/index.html`
- Config: `src/main/resources/application.properties`
- Build: `pom.xml`

## Quick Start
```sh
# Build and run
mvn spring-boot:run
# Or build JAR and run
mvn package
java -jar target/nextgenz-school-website-0.0.1-SNAPSHOT.jar
```

---
For more, see https://agents.md/ for agent authoring best practices.
