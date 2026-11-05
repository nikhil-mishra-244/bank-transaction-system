
# 🏦 Bank Transaction Management System

A RESTful backend application developed using **Java 17 and Spring Boot** to manage users, banking transactions, account balances, and transaction history.

The application provides secure JWT authentication, CREDIT and DEBIT transactions, transaction filtering, user profile management, administrative statistics, and external transaction imports.

## 🚀 Features

- User registration and login.
- JWT-based authentication and authorization.
- Role-based access control.
- View and update user profiles.
- Change account password.
- Create CREDIT and DEBIT transactions.
- Automatic account balance updates.
- View transaction history.
- Filter transactions by type, status, and date range.
- Soft-delete transactions.
- View current account balance.
- Transaction summary and statistics.
- Admin dashboard.
- Import transactions from an external API using Beeceptor.
- Request validation and exception handling.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot | Backend framework |
| Spring Web | REST API development |
| Spring Data JPA | Database operations |
| Hibernate | ORM framework |
| MySQL | Database |
| Spring Security | Authentication and authorization |
| JWT | Token-based authentication |
| Jakarta Validation | Request validation |
| Maven | Dependency management |
| Postman | API testing |
| Beeceptor | External API mocking |

## 📁 Project Structure

```text
src/main/java/
└── com/weetech/bank_transaction_system/
    ├── config/
    ├── controller/
    ├── dto/
    ├── entity/
    ├── enums/
    ├── exception/
    ├── repository/
    ├── security/
    ├── service/
    └── specification/

src/main/resources/
└── application.properties
```

## 🌐 API Endpoints

**Base URL:**

```text
http://localhost:8080
```

### 🔐 1. Authentication APIs

Base Path: `/api/v1/auth`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/auth/register` | Register a new user |
| POST | `/api/v1/auth/login` | Login and generate JWT token |

### 👤 2. User APIs

Base Path: `/api/v1/users`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/users/profile` | Get logged-in user profile |
| PUT | `/api/v1/users/profile` | Update user profile |
| PUT | `/api/v1/users/change-password` | Change account password |
| POST | `/api/v1/users/logout` | Logout user |

### 💳 3. Transaction APIs

Base Path: `/api/v1/transactions`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/transactions` | Create a transaction |
| GET | `/api/v1/transactions` | Get and filter transactions |
| DELETE | `/api/v1/transactions/{transactionId}` | Soft-delete a transaction |
| GET | `/api/v1/transactions/summary` | Get transaction summary |
| GET | `/api/v1/transactions/balance` | Get current account balance |
| POST | `/api/v1/transactions/import` | Import external transactions |

### 📊 4. Admin APIs

Base Path: `/api/v1/admin`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/admin/dashboard` | Get admin dashboard statistics |

**Optional Date Filters:**

```http
GET /api/v1/admin/dashboard?fromDate=2026-11-01&toDate=2026-11-30
```

### 🔎 Transaction Filtering and Pagination

The transaction listing API supports the following query parameters:

| Parameter | Description |
|---|---|
| `type` | Filter by CREDIT or DEBIT |
| `status` | Filter by PENDING, SUCCESS, or FAILED |
| `startDate` | Filter transactions from a date and time |
| `endDate` | Filter transactions up to a date and time |
| `page` | Page number (default: 0) |
| `size` | Number of records per page (default: 10) |

**Example:**

```http
GET /api/v1/transactions?type=DEBIT&status=SUCCESS&page=0&size=10
```

### 🔑 Authentication

Protected endpoints require a valid JWT Bearer token.

```http
Authorization: Bearer <your-jwt-token>
```

## 📝 Transaction Examples

### Create Transaction

**Endpoint:**

```http
POST /api/v1/transactions
```

**Request Body:**

```json
{
  "amount": 5000.00,
  "transactionType": "CREDIT",
  "description": "Salary Received"
}
```

**Supported Transaction Types:**

- `CREDIT` – Adds money to the account.
- `DEBIT` – Deducts money from the account.

**Transaction Statuses:**

- `PENDING`
- `SUCCESS`
- `FAILED`

### Transaction Summary

**Endpoint:**

```http
GET /api/v1/transactions/summary
```

**Example Response:**

```json
{
  "totalCredit": 55000.00,
  "totalDebit": 17000.00,
  "currentBalance": 38000.00,
  "totalTransactions": 4,
  "successfulTransactions": 3,
  "failedTransactions": 1,
  "pendingTransactions": 0
}
```

### Current Balance

**Endpoint:**

```http
GET /api/v1/transactions/balance
```

**Example Response:**

```json
{
  "balance": 38000.00
}
```

Returns the current authenticated user's account balance.

## 🌍 External Transaction Import

The application supports importing transactions from an external REST API using Beeceptor.

**Import Endpoint:**

```http
POST /api/v1/transactions/import
```

**Request Body:**

```json
{
  "url": "https://weetech-practical.free.beeceptor.com/transactions"
}
```

**Sample External API Response:**

```json
[
  {
    "externalId": "EXT101",
    "amount": 1500.00,
    "transactionType": "CREDIT",
    "description": "Salary",
    "transactionDate": "2026-11-05T10:30:00"
  },
  {
    "externalId": "EXT102",
    "amount": 500.00,
    "transactionType": "DEBIT",
    "description": "Shopping",
    "transactionDate": "2026-11-05T11:30:00"
  }
]
```

**Import Process:**

1. The authenticated user sends the external API URL.
2. The application fetches transaction data from the external API.
3. The response is mapped to the application's transaction DTO.
4. Transactions are processed using the existing transaction creation logic.
5. Account balances are updated according to transaction types.

**Implementation Notes:**

- The user ID is obtained from the JWT authentication principal.
- External transaction IDs are not stored in the database.
- External transaction dates are not currently mapped.
- Imported transactions follow existing CREDIT and DEBIT business rules.
- The import endpoint currently returns a success message after processing.

## 📋 Business Rules

- Transaction amounts must be greater than zero.
- CREDIT transactions increase the account balance.
- DEBIT transactions decrease the account balance.
- DEBIT transactions require sufficient account balance.
- FAILED transactions do not affect the account balance.
- Transactions support PENDING, SUCCESS, and FAILED statuses.
- Deleted transactions are soft-deleted.
- Users can access their own transaction information according to authorization rules.
- Admin APIs are restricted according to user roles.

## 📡 HTTP Status Codes

| Status Code | Description |
|---|---|
| 200 OK | Request successful |
| 201 Created | Resource created |
| 204 No Content | Resource deleted successfully |
| 400 Bad Request | Invalid request |
| 401 Unauthorized | Authentication required |
| 403 Forbidden | Access denied |
| 404 Not Found | Resource not found |
| 500 Internal Server Error | Server error |

## 🔮 Future Enhancements

- [ ] Detailed external transaction import reports.
- [ ] External transaction date mapping.
- [ ] Duplicate transaction detection.
- [ ] External API URL validation and request timeouts.
- [ ] Improved batch import error handling.
- [ ] Automated unit and integration testing.
- [ ] Swagger / OpenAPI documentation.

## ⚙️ Getting Started

### Prerequisites

- Java 17 or later
- Maven
- MySQL
- IntelliJ IDEA or any Java IDE
- Postman

### 1. Clone the Repository

```bash
git clone <your-repository-url>
```

### 2. Open the Project

Open the cloned project in IntelliJ IDEA or your preferred Java IDE.

### 3. Create MySQL Database

```sql
CREATE DATABASE bank_transaction_db;
```

### 4. Configure Database

Update your `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank_transaction_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### 5. Build the Project

```bash
mvn clean install
```

### 6. Run the Application

```bash
mvn spring-boot:run
```

### 7. Access the Application

```text
http://localhost:8080
```

Use Postman to test the available REST API endpoints.

## 👨‍💻 Author

**Bank Transaction Management System**

Developed using Java 17, Spring Boot, Spring Security, Spring Data JPA, Hibernate, and MySQL.
