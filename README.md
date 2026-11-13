
# Hospital Management System using Spring Boot and JWT Authentication

## 1. Project Overview

The **Hospital Management System (HMS)** is a backend application developed using Spring Boot, Spring Security, JWT Authentication and MySQL.

This project is designed as a student mini-project to understand backend application development, REST APIs, authentication, authorization, database connectivity, CRUD operations and unit testing.

The application allows users to register and log in. After successful login, the application generates a JWT (JSON Web Token), which is used to access secured patient management APIs.

The project is intentionally limited to two main modules:

1. Authentication Module
2. Patient Management Module

## 2. Project Objectives

The main objectives of this project are:

- To understand Spring Boot application structure.
- To implement user registration and login.
- To securely store passwords using BCrypt hashing.
- To generate and validate JWT tokens.
- To secure REST APIs using Spring Security.
- To perform CRUD operations on patient records.
- To connect a Spring Boot application with MySQL.
- To implement unit and repository testing.
- To test REST APIs using Postman.
- To understand Git branching, feature development and merging.

## 3. Technologies Used

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 4.1.2 | Backend application framework |
| Spring Web MVC | Developing REST APIs |
| Spring Security | API security and authentication |
| JWT (JJWT 0.12.6) | Token-based authentication |
| Spring Data JPA | Database operations |
| Hibernate | ORM implementation |
| MySQL 8 | Relational database |
| Lombok | Reducing boilerplate code |
| Maven 3.10.0 | Dependency and build management |
| Jakarta Validation | Request data validation |
| JUnit 5 | Unit testing |
| Mockito | Mocking dependencies during testing |
| AssertJ | Writing readable test assertions |
| Postman | API testing |
| Git and GitHub | Version control and project management |

## 4. Project Modules

### 4.1 Authentication Module

The Authentication Module manages user registration and login.

**Main functionalities:**

- Register a new user.
- Validate registration details.
- Check whether an email is already registered.
- Encode passwords using BCrypt.
- Verify login credentials.
- Generate JWT tokens after successful login.
- Validate JWT tokens for secured requests.
- Load registered user details for Spring Security.

**Authentication APIs:**

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Log in and generate JWT |

### 4.2 Patient Management Module

The Patient Module manages patient records through REST APIs.

**Main functionalities:**

- Add a new patient.
- View all patients.
- View a patient using their ID.
- Update patient details.
- Delete a patient record.

**Patient APIs:**

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/patients` | Add a patient |
| GET | `/api/patients` | Get all patients |
| GET | `/api/patients/{id}` | Get patient by ID |
| PUT | `/api/patients/{id}` | Update patient details |
| DELETE | `/api/patients/{id}` | Delete a patient |

All patient APIs are protected using JWT authentication.

## 5. Project Folder Structure

```text
hospital-management-system/
│
├── src/
│   ├── main/
│   │   ├── java/com/example/hms/
│   │   │   │
│   │   │   ├── config/
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   ├── JwtService.java
│   │   │   │   ├── PasswordConfig.java
│   │   │   │   └── SecurityConfig.java
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   └── PatientController.java
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── LoginResponse.java
│   │   │   │   ├── PatientDto.java
│   │   │   │   ├── RegisterRequest.java
│   │   │   │   └── RegisterResponse.java
│   │   │   │
│   │   │   ├── entity/
│   │   │   │   ├── Patient.java
│   │   │   │   ├── Role.java
│   │   │   │   └── User.java
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   ├── PatientRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   │
│   │   │   ├── service/
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── CustomUserDetailsService.java
│   │   │   │   └── PatientService.java
│   │   │   │
│   │   │   └── HospitalManagementSystemApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/com/example/hms/
│           │
│           ├── config/
│           │   ├── JwtAuthenticationFilterTest.java
│           │   ├── JwtServiceTest.java
│           │   ├── PasswordConfigTest.java
│           │   └── SecurityConfigTest.java
│           │
│           ├── repository/
│           │   ├── PatientRepositoryTest.java
│           │   └── UserRepositoryTest.java
│           │
│           ├── service/
│           │   ├── AuthLoginTest.java
│           │   ├── AuthRegistrationTest.java
│           │   ├── CustomUserDetailsServiceTest.java
│           │   └── PatientServiceTest.java
│           │
│           └── HospitalManagementSystemApplicationTests.java
│
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
└── README.md
```

## 6. Package and Class Responsibilities

### 6.1 Config Package

Contains security and configuration-related classes.

- **SecurityConfig.java:** Configures Spring Security, public authentication endpoints, protected patient endpoints and stateless sessions.
- **JwtAuthenticationFilter.java:** Intercepts requests, reads JWT tokens, validates them and sets authentication in the Spring Security context.
- **JwtService.java:** Generates JWT tokens, extracts usernames and validates tokens.
- **PasswordConfig.java:** Provides the BCrypt password encoder bean.

### 6.2 Controller Package

Handles incoming HTTP requests and returns API responses.

- **AuthController.java:** Handles user registration and login.
- **PatientController.java:** Handles patient CRUD operations.

### 6.3 DTO Package

DTO (Data Transfer Object) classes transfer data between the client and application without directly exposing database entities.

- **RegisterRequest.java:** Accepts registration details.
- **RegisterResponse.java:** Returns registration information.
- **LoginRequest.java:** Accepts login credentials.
- **LoginResponse.java:** Returns login information and JWT token.
- **PatientDto.java:** Transfers patient information.

### 6.4 Entity Package

Contains Java classes mapped to database tables.

- **User.java:** Represents a registered user.
- **Role.java:** Defines user roles such as ADMIN and USER.
- **Patient.java:** Represents a patient record.

### 6.5 Repository Package

Contains interfaces responsible for database operations.

- **UserRepository.java:** Provides user-related database operations, including searching by email.
- **PatientRepository.java:** Provides patient CRUD database operations.

### 6.6 Service Package

Contains the application's business logic.

- **AuthService.java:** Handles registration, password encoding, login verification and token generation.
- **PatientService.java:** Handles patient creation, retrieval, updating and deletion.
- **CustomUserDetailsService.java:** Loads registered user information from the database for Spring Security authentication.

## 7. Database Design

The project uses MySQL and contains two main tables.

### 7.1 Users Table

Stores registered user information.

| Column | Description |
|---|---|
| id | Primary key |
| name | User's full name |
| email | Unique email address |
| password | BCrypt-encoded password |
| role | User role |

### 7.2 Patients Table

Stores patient information.

| Column | Description |
|---|---|
| id | Primary key |
| name | Patient name |
| age | Patient age |
| gender | Patient gender |
| disease | Disease or medical condition |
| address | Patient address |
| phone_number | Contact number |

Hibernate automatically creates or updates the tables using the configured JPA settings.

## 8. JWT Authentication

JWT stands for **JSON Web Token**. It is used to authenticate users and secure APIs without maintaining server-side login sessions.

### 8.1 How JWT Authentication Works

1. A user registers using their name, email and password.
2. The application validates the registration details.
3. The password is encoded using BCrypt and the user is saved in MySQL.
4. The user sends their email and password to the login API.
5. The application retrieves the user and verifies the credentials.
6. If the credentials are correct, a JWT token is generated.
7. The token is returned to the client.
8. The client sends the token in the Authorization header when accessing patient APIs.
9. The JWT authentication filter extracts and validates the token.
10. The user is loaded from the database using CustomUserDetailsService.
11. If the token is valid, authentication is stored in the Spring Security context.
12. Spring Security allows the authenticated request to continue to the patient controller.

### 8.2 Authorization Header

```text
Authorization: Bearer <your_jwt_token>
```

### 8.3 Security Features

- Passwords are stored in BCrypt-encoded form.
- JWT tokens are signed using a secret key.
- Token expiration is configured.
- Authentication endpoints are publicly accessible.
- Patient APIs require authentication.
- Spring Security uses stateless session management.
- CSRF protection is disabled for this stateless API configuration.

## 9. Database Configuration

The application uses the following database configuration in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hms_jwt_db
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.application.name=hospital-management-system
server.port=8080

jwt.secret=${JWT_SECRET}
jwt.expiration=86400000
```

**Important:**

- Create a MySQL database named `hms_jwt_db`.
- Configure the `DB_PASSWORD` environment variable with your MySQL password.
- Configure the `JWT_SECRET` environment variable with a secure Base64-encoded secret.
- The JWT expiration value is in milliseconds. `86400000` represents 24 hours.
- Never commit actual database passwords or JWT secrets to GitHub.

## 10. How to Run the Project

### Prerequisites

Install the following software:

- Java JDK 21
- Maven 3.10.0 or compatible Maven version
- MySQL 8
- IntelliJ IDEA or another Java IDE
- Postman
- Git

### Step 1: Clone the Repository

```bash
git clone https://github.com/Hemang71006/BL_hospital-management-system.git
```

### Step 2: Navigate to the Project

```bash
cd BL_hospital-management-system
```

### Step 3: Switch to the Develop Branch

The completed application source code is maintained in the `develop` branch.

```bash
git switch develop
git pull origin develop
```

### Step 4: Create the MySQL Database

Open MySQL and execute:

```sql
CREATE DATABASE hms_jwt_db;
```

### Step 5: Configure Environment Variables

Configure the following environment variables on your system:

```text
DB_PASSWORD = your_mysql_password
JWT_SECRET = your_base64_encoded_secret
```

Do not publish their actual values.

### Step 6: Build the Project

```bash
mvn clean install
```

### Step 7: Run the Application

```bash
mvn spring-boot:run
```

The application starts at:

```text
http://localhost:8080
```

## 11. API Testing Using Postman

The APIs can be tested using Postman.

### 11.1 Register User

**Method:** POST

**URL:**

```text
http://localhost:8080/api/auth/register
```

**Request Body:**

```json
{
  "name": "Admin User",
  "email": "admin@gmail.com",
  "password": "Admin@123"
}
```

The application registers the user and stores the encoded password in MySQL.

Public registration assigns the default USER role. Users cannot select their own role during registration.

### 11.2 Login User

**Method:** POST

**URL:**

```text
http://localhost:8080/api/auth/login
```

**Request Body:**

```json
{
  "email": "admin@gmail.com",
  "password": "Admin@123"
}
```

The response contains user information and the generated JWT token.

Example response structure:

```json
{
  "message": "Login successful",
  "userId": 1,
  "name": "Admin User",
  "email": "admin@gmail.com",
  "role": "USER",
  "token": "your_jwt_token_here"
}
```

### 11.3 Add Patient

**Method:** POST

**URL:**

```text
http://localhost:8080/api/patients
```

**Authorization:** Bearer Token

**Request Body:**

```json
{
  "name": "Rahul Kumar",
  "age": 27,
  "gender": "Male",
  "disease": "Fever",
  "address": "Chennai",
  "phoneNumber": "9876543210"
}
```

### 11.4 Get All Patients

**Method:** GET

**URL:**

```text
http://localhost:8080/api/patients
```

**Authorization:** Bearer Token

### 11.5 Get Patient By ID

**Method:** GET

**URL:**

```text
http://localhost:8080/api/patients/1
```

**Authorization:** Bearer Token

### 11.6 Update Patient

**Method:** PUT

**URL:**

```text
http://localhost:8080/api/patients/1
```

**Authorization:** Bearer Token

**Request Body:**

```json
{
  "name": "Rahul Kumar",
  "age": 28,
  "gender": "Male",
  "disease": "Viral Fever",
  "address": "Chennai",
  "phoneNumber": "9876543210"
}
```

### 11.7 Delete Patient

**Method:** DELETE

**URL:**

```text
http://localhost:8080/api/patients/1
```

**Authorization:** Bearer Token

## 12. HTTP Status Codes

The following HTTP status codes are relevant to the application's API operations.

| Status Code | Meaning |
|---|---|
| 200 OK | Request successful |
| 201 Created | Resource created successfully |
| 204 No Content | Resource deleted successfully |
| 400 Bad Request | Invalid request |
| 401 Unauthorized | Missing or invalid authentication |
| 404 Not Found | Requested resource not found |
| 500 Internal Server Error | Unexpected server error |

The actual status code depends on the controller response and exception handling configured in the application.

## 13. Project Architecture

The application follows a layered architecture.

### 13.1 Layered Architecture

```text
              Client / Postman
                     |
                     v
                Controller
                     |
                     v
                  Service
                     |
                     v
                Repository
                     |
                     v
                   MySQL
```

### 13.2 JWT Security Flow

```text
             User Registration
                     |
                     v
               AuthController
                     |
                     v
                 AuthService
                     |
                     v
             Encode Password
                 using BCrypt
                     |
                     v
                 MySQL
                     |
                     |
                User Login
                     |
                     v
               AuthController
                     |
                     v
                 AuthService
                     |
                     v
            Verify Credentials
                     |
                     v
               Generate JWT
                     |
                     v
               Return Token
                     |
                     v
            Client Sends Token
                     |
                     v
          JWT Authentication Filter
                     |
                     v
              Validate Token
                     |
                     v
          CustomUserDetailsService
                     |
                     v
          Spring Security Context
                     |
                     v
             Patient Controller
                     |
                     v
              Patient Service
                     |
                     v
             Patient Repository
                     |
                     v
                  MySQL
```

## 14. Testing and Verification

The project includes automated tests using JUnit 5, Mockito and AssertJ.

Tests are organized according to the application packages.

### 14.1 Application Context Testing

**HospitalManagementSystemApplicationTests.java**

- Verifies that the Spring Boot application context loads successfully.

### 14.2 Repository Testing

**UserRepositoryTest.java**

Tests user database operations:

- Save and retrieve a user by email.
- Check whether an email exists.
- Verify behavior when an email does not exist.

**PatientRepositoryTest.java**

Tests patient database operations:

- Save a patient.
- Find a patient by ID.
- Update patient information.
- Delete a patient.

### 14.3 Patient Service Testing

**PatientServiceTest.java**

Tests patient business logic:

- Add a patient.
- Retrieve all patients.
- Retrieve a patient by ID.
- Update patient details.
- Delete a patient.
- Handle missing patient records during retrieval, updating and deletion.

### 14.4 Authentication Testing

**AuthRegistrationTest.java**

Tests user registration:

- Successful registration.
- Password encoding.
- Default USER role assignment.
- Email normalization.
- Duplicate email rejection.
- Registration response details.

**AuthLoginTest.java**

Tests user login:

- Successful login.
- Password verification.
- Rejection of incorrect passwords.
- Rejection of unknown email addresses.
- Email normalization.
- Login response details.

### 14.5 JWT and Security Testing

**JwtServiceTest.java**

Tests JWT functionality:

- Token generation.
- Username extraction.
- Validation of a valid token.
- Rejection of a token for a different user.
- Expired token handling.

**JwtAuthenticationFilterTest.java**

Tests JWT authentication filter behavior:

- Requests without an Authorization header.
- Requests with a non-Bearer Authorization header.
- Authentication using a valid token.
- Handling of an invalid token.
- Preservation of existing authentication.

**CustomUserDetailsServiceTest.java**

Tests user loading for Spring Security:

- Loading an existing user.
- Verifying the username.
- Verifying the password and role.
- Handling a user that does not exist.

**PasswordConfigTest.java**

Tests password configuration:

- Creation of the BCrypt password encoder.
- Password encoding.
- Matching the correct password.
- Rejecting an incorrect password.

**SecurityConfigTest.java**

Contains a basic configuration object-creation test.

### 14.6 Running Tests

Run all tests using:

```bash
mvn clean test
```

Run a specific test class using:

```bash
mvn test -Dtest=PatientServiceTest
```

Run multiple test classes using:

```bash
mvn test -Dtest=JwtServiceTest,JwtAuthenticationFilterTest
```

The Maven test report displays the number of tests executed, failures, errors and skipped tests.

## 15. Git and GitHub Workflow

The project uses Git feature branches to organize development.

The main development branch is:

```text
develop
```

Feature branches:

```text
feature/database-configuration
feature/entity-repository
feature/patient-crud
feature/auth-registration
feature/auth-login
feature/jwt-security
```

Each feature was developed and tested independently before being merged into `develop`.

### Git Commands

Check the current branch:

```bash
git branch
```

Check working tree status:

```bash
git status
```

Switch to develop:

```bash
git switch develop
```

Pull the latest changes:

```bash
git pull origin develop
```

Merge a feature branch:

```bash
git merge --no-ff feature/jwt-security
```

Push develop to GitHub:

```bash
git push origin develop
```

The feature branches are retained after merging.

## 16. Limitations

This version intentionally focuses on authentication and patient CRUD operations.

The following features are not included:

- Doctor management.
- Appointment management.
- Billing and payment management.
- Frontend or dashboard.
- Google login.
- Advanced role-based authorization.
- Pagination and search functionality.

## 17. Future Enhancements

The application can be extended in the future with:

- Doctor management.
- Appointment scheduling.
- Role-based access control.
- Frontend using React or Angular.
- Patient search and filtering.
- Pagination.
- Swagger API documentation.

## 18. Conclusion

The Hospital Management System is a backend application developed using Spring Boot, Spring Security, JWT, Spring Data JPA and MySQL.

It demonstrates important backend development concepts such as REST API creation, layered architecture, database connectivity, user authentication, password encoding, JWT token validation, secured CRUD operations and automated testing.

The project is intentionally limited to two modules: Authentication and Patient Management. This makes it suitable for student learning, practical demonstrations, internship preparation and project presentations.

## 19. One-Line Project Summary

A simple Hospital Management System backend built using Spring Boot, Spring Security, JWT Authentication and MySQL, providing secure user authentication and patient CRUD operations.
