# User Management System REST API

## 📌 Overview
This is a **User Management System REST API** built using **Spring Boot**. It provides role-based access control, allowing **Admin** users to manage all users while **regular users** can only access and modify their own profiles.

## ✨ Features
- **User Authentication & Authorization** (JWT Authentication)
- **Role-Based Access Control (RBAC)**
- **User Registration with Role Selection**
- **CRUD Operations for Users** (Admins only)
- **Users can update/delete their own profile**
- **Pagination & Sorting for user retrieval**
- **Exception Handling**
- **Dockerized MySQL Database**
  
## 🛠️ Tech Stack
- **Java 20+**
- **Spring Boot 3.4.3**
- **Spring Security (JWT Authentication)**
- **Spring Data JPA**
- **MySQL (Docker Container)**
- **Maven**
- **Swagger / Springdoc OpenAPI**
- **JUnit & Mockito** for unit testing
  
## 🚀 Setup & Installation

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/ShrunalNimje/User_Management_System_RestAPI.git
cd User_Management_System_RestAPI
```

### 2️⃣ Configure Database
This project uses **MySQL in a Docker container**. Ensure you have **Docker Desktop** installed.

Run the following command to start the MySQL container:
```sh
docker run --name user-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=user_db -p 3306:3306 -d mysql:latest
```

### 3️⃣ Update `application.properties`
Modify database configurations if needed:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/user_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```

### 4️⃣ Build & Run the Application
```sh
mvn clean install
mvn spring-boot:run
```

## 📌 API Endpoints

### 🔹 Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/register/` | Register a new user with a default role user |
| `POST` | `/login/` | login an user with right credentials |

### 🔹 Admin Endpoints (Requires `ADMIN` Role)
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/admin/users/` | Get all users (with pagination & sorting) |
| `GET` | `/admin/users/{id}/` | Get user by ID |
| `PUT` | `/admin/users/{id}/` | Update user by ID |
| `DELETE` | `/admin/users/{id}/` | Delete user by ID |
| `DELETE` | `/admin/users/` | Delete all users |
| `POST` | `/admin/users/` | Create an user |
| `GET` | `/admin/dashboard/` | Get admin profile |

### 🔹 User Endpoints (Requires Authentication)
| Method | Endpoint | Description |
|--------|---------|-------------|
| `PUT` | `/user/update/` | Update own profile |
| `DELETE` | `/user/delete/` | Delete own account |
| `GET`  | `/user/profile/` | Get logged-in user profile |

## 📘 Swagger Documentation
Access the interactive API docs at:

```sh
http://localhost:8080/swagger-ui/index.html
```

## 🧪 Testing
Unit tests for the service layer are written using **JUnit** and **Mockito**.

## 🔐 Authentication
**JWT Authentication:** Secure endpoints using JWT tokens.
After login, include the token in the Authorization header as:

```sh
Authorization: Bearer <token>
```

## 📖 Pagination & Sorting
To fetch paginated results with sorting:
```http
GET /admin/users/?page=0&size=5&sort=id,asc
```

## 💡 Author
Developed by **[Shrunal Nimje](https://github.com/ShrunalNimje)**

---

🔥 **Feel free to fork and contribute!** 🚀
