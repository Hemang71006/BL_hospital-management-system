# Hospital Management System using Spring Boot and JWT Authentication

## 1. Project Overview

The **Hospital Management System** is a simple backend application developed using Spring Boot, Spring Security, JWT Authentication and MySQL.

This project is designed as a student mini-project to understand backend application development, REST APIs, authentication, authorization, database connectivity and CRUD operations.

The application allows users to register and log in. After successful login, the application generates a JWT (JSON Web Token), which is used to access secured patient management APIs.

The project is intentionally limited to two main modules:

1. Authentication Module
2. Patient Management Module

## 2. Project Objectives

The main objectives of this project are:

- To understand Spring Boot application structure.
- To implement user registration and login.
- To securely store passwords using BCrypt encryption.
- To generate and validate JWT tokens.
- To secure REST APIs using Spring Security.
- To perform CRUD operations on patient records.
- To connect a Spring Boot application with MySQL.
- To test REST APIs using Postman.

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
| Postman | API testing |

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
│   └── main/
│       ├── java/
│       │   └── com/example/hms/
│       │       │
│       │       ├── config/
│       │       │   ├── SecurityConfig.java
│       │       │   ├── JwtAuthenticationFilter.java
│       │       │   ├── JwtService.java
│       │       │   └── PasswordConfig.java
│       │       │
│       │       ├── controller/
│       │       │   ├── AuthController.java
│       │       │   └── PatientController.java
│       │       │
│       │       ├── dto/
│       │       │   ├── RegisterRequest.java
│       │       │   ├── RegisterResponse.java
│       │       │   ├── LoginRequest.java
│       │       │   ├── LoginResponse.java
│       │       │   └── PatientDto.java
│       │       │
│       │       ├── entity/
│       │       │   ├── User.java
│       │       │   ├── Role.java
│       │       │   └── Patient.java
│       │       │
│       │       ├── repository/
│       │       │   ├── UserRepository.java
│       │       │   └── PatientRepository.java
│       │       │
│       │       ├── service/
│       │       │   ├── AuthService.java
│       │       │   ├── PatientService.java
│       │       │   └── CustomUserDetailsService.java
│       │       │
│       │       └── HospitalManagementApplication.java
│       │
│       └── resources/
│           └── application.properties
│
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
└── README.md
```

## 6. Package and Class Responsibilities

### Config Package

Contains security and configuration-related classes.

- **SecurityConfig.java:** Configures Spring Security, public authentication endpoints, protected patient endpoints and stateless sessions.
- **JwtAuthenticationFilter.java:** Intercepts requests, reads JWT tokens, validates them and sets authentication in the Spring Security context.
- **JwtService.java:** Generates JWT tokens, extracts usernames and validates tokens.
- **PasswordConfig.java:** Provides the BCrypt password encoder bean.

### Controller Package

Handles incoming HTTP requests and returns API responses.

- **AuthController.java:** Handles user registration and login.
- **PatientController.java:** Handles patient CRUD operations.

### DTO Package

DTO (Data Transfer Object) classes transfer data between the client and application without directly exposing database entities.

- **RegisterRequest.java:** Accepts registration details.
- **RegisterResponse.java:** Returns registration information.
- **LoginRequest.java:** Accepts login credentials.
- **LoginResponse.java:** Returns login information and JWT token.
- **PatientDto.java:** Transfers patient information.

### Entity Package

Contains Java classes mapped to database tables.

- **User.java:** Represents a registered user.
- **Role.java:** Defines user roles such as ADMIN and USER.
- **Patient.java:** Represents a patient record.

### Repository Package

Contains interfaces responsible for database operations.

- **UserRepository.java:** Provides user-related database operations, including searching by email.
- **PatientRepository.java:** Provides patient CRUD database operations.

### Service Package

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

### How JWT Authentication Works

1. A user registers using their name, email and password.
2. The application encodes the password using BCrypt and saves the user in MySQL.
3. The user sends their email and password to the login API.
4. The application verifies the login credentials.
5. If the credentials are correct, a JWT token is generated.
6. The user sends the token in the Authorization header when accessing patient APIs.
7. The JWT authentication filter validates the token.
8. If the token is valid, Spring Security allows the request to continue.

### Authorization Header

```http
Authorization: Bearer <your_jwt_token>
```

### Security Features

- Passwords are stored in BCrypt-encoded form.
- JWT tokens are signed using a secret key.
- Token expiration is configured.
- Authentication endpoints are publicly accessible.
- Patient APIs require authentication.
- Spring Security uses stateless session management.

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
- Never commit actual database passwords or JWT secrets to GitHub.

## 10. How to Run the Project

### Prerequisites

Install the following software:

- Java JDK 21
- Maven 3.10.0 or compatible Maven version
- MySQL 8
- IntelliJ IDEA or another Java IDE
- Postman

### Step 1: Clone the Repository

```bash
git clone https://github.com/Hemang71006/BL_hospital-management-system.git
```

### Step 2: Navigate to the Project

```bash
cd BL_hospital-management-system
```

### Step 3: Switch to the Develop Branch

The application source code is maintained in the `develop` branch.

```bash
git switch develop
```

### Step 4: Create the MySQL Database

Open MySQL and execute:

```sql
CREATE DATABASE hms_jwt_db;
```

### Step 5: Configure Environment Variables

Configure these environment variables on your system:

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

The application registers the user and stores the encoded password in MySQL. Public registration assigns the default USER role.

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

The application uses HTTP status codes to indicate API results.

| Status Code | Meaning |
|---|---|
| 200 OK | Request successful |
| 201 Created | Resource created successfully |
| 204 No Content | Resource deleted successfully |
| 400 Bad Request | Invalid request |
| 401 Unauthorized | Missing or invalid authentication |
| 404 Not Found | Requested resource not found |
| 500 Internal Server Error | Unexpected server error |

## 13. Project Architecture

The application follows a layered architecture.

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

**JWT Security Flow:**

```text
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
    Validate JWT Token
            |
            v
    Spring Security
            |
            v
   Patient Controller
```

## 14. Testing and Verification

The project was tested using Postman and Maven.

The following operations were verified:

- User registration.
- Password encoding using BCrypt.
- User login.
- JWT token generation.
- Accessing patient APIs using a valid token.
- Blocking access without a valid token.
- Adding patient records.
- Retrieving all patients.
- Retrieving a patient by ID.
- Updating patient details.
- Deleting patient records.

The Maven build and test command is:

```bash
mvn clean test
```

## 15. Limitations

This version intentionally focuses on authentication and patient CRUD operations.

The following features are not included:

- Doctor management.
- Appointment management.
- Billing and payment management.
- Frontend or dashboard.
- Google login.
- Advanced role-based authorization.
- Pagination and search functionality.

## 16. Future Enhancements

The application can be extended in the future with:

- Doctor management.
- Appointment scheduling.
- Role-based access control.
- Frontend using React or Angular.
- Patient search and filtering.
- Pagination.
- Swagger API documentation.

## 17. Conclusion

The Hospital Management System is a small backend application developed using Spring Boot, Spring Security, JWT, Spring Data JPA and MySQL.

It demonstrates important backend development concepts such as REST API creation, layered architecture, database connectivity, user authentication, password encoding, JWT token validation and secured CRUD operations.

The project is intentionally limited to two modules: Authentication and Patient Management. This makes it suitable for student learning, practical demonstrations, internship preparation and project presentations.

## 18. One-Line Project Summary

A simple Hospital Management System backend built using Spring Boot, Spring Security, JWT Authentication and MySQL, providing secure user authentication and patient CRUD operations.
