
# 🏦 Bank Transaction Management System

A RESTful backend application developed using **Java 17 and Spring Boot** to manage users, banking transactions, account balances, and transaction history.

The application supports secure authentication, CREDIT and DEBIT transactions, transaction filtering, administrative statistics, and importing transactions from an external API.

## 🚀 Features

- User registration and management.
- JWT-based authentication and authorization.
- Role-based access control.
- Create CREDIT and DEBIT transactions.
- Automatic account balance updates.
- Transaction history and filtering.
- Filter transactions by type, status, and date range.
- Soft delete transactions.
- View current account balance.
- Transaction summary and statistics.
- Admin dashboard.
- Import transactions from an external API using Beeceptor.
- Request validation and error handling.

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
└── com/example/banktransaction/
    ├── config/
    ├── controller/
    ├── dto/
    ├── entity/
    ├── exception/
    ├── repository/
    ├── security/
    ├── service/
    └── BankTransactionApplication.java

src/main/resources/
└── application.properties
```

## 🌐 API Endpoints

**Base URL:**

```text
http://localhost:8080
```

### 👤 User Management

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/users/register` | Register a new user |
| GET | `/api/v1/users` | Get all users |
| GET | `/api/v1/users/{id}` | Get user by ID |
| DELETE | `/api/v1/users/{id}` | Delete a user |

### 🔐 Authentication

Protected endpoints require a valid JWT token.

```http
Authorization: Bearer <your-jwt-token>
```

### 💳 Transaction Management

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/transactions` | Create a transaction |
| GET | `/api/v1/transactions` | Get transaction history |
| DELETE | `/api/v1/transactions/{transactionId}` | Soft-delete a transaction |
| GET | `/api/v1/transactions/summary` | Get transaction summary |
| GET | `/api/v1/transactions/balance` | Get current balance |
| POST | `/api/v1/transactions/import` | Import external transactions |

### 📊 Admin Dashboard

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/admin/dashboard` | Get system-wide statistics |

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

**Transaction Types:**

- `CREDIT` – Adds money to the account.
- `DEBIT` – Deducts money from the account.

**Transaction Statuses:**

- `PENDING`
- `SUCCESS`
- `FAILED`

### Filter Transactions

Transactions can be filtered by type, status, and date range.

| Parameter | Example |
|---|---|
| type | `CREDIT` |
| status | `SUCCESS` |
| startDate | `2026-11-01T00:00:00` |
| endDate | `2026-11-30T23:59:59` |

**Example:**

```http
GET /api/v1/transactions?type=DEBIT&status=SUCCESS
```

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

```http
GET /api/v1/transactions/balance
```

Returns the current authenticated user's account balance.

## 🌍 External Transaction Import

The application supports importing transactions from an external REST API using Beeceptor.

**Endpoint:**

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
2. The application fetches transaction data.
3. The response is mapped to the transaction DTO.
4. Transactions are processed using the existing transaction creation logic.
5. Account balances are updated according to transaction types.

**Implementation Notes:**

- The user ID is obtained from the JWT authentication principal.
- External transaction IDs are not stored in the database.
- Transaction dates are not currently mapped during import.
- Imported transactions follow existing CREDIT and DEBIT business rules.
- Only trusted external API URLs should be used.

## 📋 Business Rules

- Transaction amounts must be greater than zero.
- CREDIT transactions increase the account balance.
- DEBIT transactions decrease the account balance.
- DEBIT transactions require sufficient account balance.
- FAILED transactions do not affect the account balance.
- Transactions support PENDING, SUCCESS, and FAILED statuses.
- Deleted transactions are soft-deleted.
- Users can access their own transaction information according to authorization rules.
- Administrative APIs are restricted according to user roles.

## 📡 HTTP Status Codes

| Status Code | Description |
|---|---|
| 200 OK | Request successful |
| 201 Created | Resource created |
| 400 Bad Request | Invalid request |
| 401 Unauthorized | Authentication required |
| 403 Forbidden | Access denied |
| 404 Not Found | Resource not found |
| 500 Internal Server Error | Server error |

## 🔮 Future Enhancements

- [ ] Detailed external import reports.
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
