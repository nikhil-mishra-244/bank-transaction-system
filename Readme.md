
# 🏦 Bank Transaction System

A **Bank Transaction System** built using Java, Spring Boot, Spring Data JPA, Hibernate, and MySQL. This application provides REST APIs to manage users, handle banking transactions, track account balances, generate transaction summaries, and view administrative dashboard analytics.

It also supports importing historical transactions from external APIs.

## 📌 Table of Contents

- [Overview](#-overview)
- [Technologies Used](#-technologies-used)
- [Base URL](#-base-url)
- [User Management APIs](#-user-management-apis)
- [Transaction Management APIs](#-transaction-management-apis)
- [Transaction Summary API](#-transaction-summary-api)
- [Current Balance API](#-current-balance-api)
- [Admin Dashboard APIs](#-admin-dashboard-apis)
- [External Transaction Import API](#-external-transaction-import-api)
- [Common HTTP Status Codes](#-common-http-status-codes)
- [Business Rules](#-business-rules)
- [Future Enhancements](#-future-enhancements)
- [Author](#-author)

---

## 🚀 Overview

The Bank Transaction System is a Spring Boot REST API application that allows users to:

- Register and manage user accounts.
- Create CREDIT and DEBIT transactions.
- Track current account balances.
- View transaction history.
- Filter transactions by type, status, and date range.
- Soft delete transactions.
- Generate transaction summaries.
- View administrative dashboard analytics.
- Import historical transactions from external APIs.

---

## 🛠 Technologies Used

| Technology | Description |
|---|---|
| Java | Backend programming language |
| Spring Boot | Application framework |
| Spring Data JPA | Database interaction |
| Hibernate | ORM framework |
| MySQL | Relational database |
| REST APIs | API communication |
| Maven | Dependency management |
| Jakarta Validation | Request validation |

---

## 🌐 Base URL

```http
http://localhost:8080
```

---

# 👤 User Management APIs

**Base Endpoint:** `/api/v1/users`

### 1. Create User

**Endpoint:** `POST /api/v1/users`

Creates a new user account.

**Request Body:**

```json
{
  "name": "Jay Patel",
  "email": "jay.patel@example.com",
  "password": "Jay@12345",
  "role": "USER"
}
```

**Success Response:** `201 Created`

### 2. Get All Users

**Endpoint:** `GET /api/v1/users`

Retrieves all registered users.

**Success Response:** `200 OK`

### 3. Get User By ID

**Endpoint:** `GET /api/v1/users/{userId}`

Retrieves user details using the user ID.

**Example:**

```http
GET /api/v1/users/1
```

---

# 💳 Transaction Management APIs

**Base Endpoint:**

```http
/api/v1/users/{userId}/transactions
```

### 1. Create Transaction

**Endpoint:** `POST /api/v1/users/{userId}/transactions`

Creates a new CREDIT or DEBIT transaction.

**Request Body:**

```json
{
  "amount": 5000.00,
  "transactionType": "CREDIT",
  "description": "Salary Received"
}
```

**Supported Transaction Types:**

- `CREDIT`
- `DEBIT`

**Transaction Status:**

- `PENDING`
- `SUCCESS`
- `FAILED`

**Business Logic:**

- Transaction amount must be greater than zero.
- CREDIT transactions increase the account balance.
- DEBIT transactions decrease the account balance.
- DEBIT transactions fail when the user has insufficient balance.
- Transaction status is updated according to the processing result.
- Failed transactions do not affect the account balance.

**Success Response:** `201 Created`

### 2. Get All User Transactions

**Endpoint:** `GET /api/v1/users/{userId}/transactions`

Retrieves all non-deleted transactions belonging to a user.

**Success Response:** `200 OK`

### 3. Filter Transactions

**Endpoint:** `GET /api/v1/users/{userId}/transactions`

Supports filtering transactions using query parameters.

| Parameter | Description | Example |
|---|---|---|
| `type` | CREDIT or DEBIT | `type=CREDIT` |
| `status` | SUCCESS, FAILED or PENDING | `status=SUCCESS` |
| `startDate` | Starting transaction date | `startDate=2026-11-01T00:00:00` |
| `endDate` | Ending transaction date | `endDate=2026-11-30T23:59:59` |

**Filter by Transaction Type:**

```http
GET /api/v1/users/1/transactions?type=CREDIT
```

**Filter by Transaction Status:**

```http
GET /api/v1/users/1/transactions?status=SUCCESS
```

**Filter by Date Range:**

```http
GET /api/v1/users/1/transactions?startDate=2026-11-01T00:00:00&endDate=2026-11-30T23:59:59
```

**Combine Multiple Filters:**

```http
GET /api/v1/users/1/transactions?type=DEBIT&status=SUCCESS&startDate=2026-11-01T00:00:00&endDate=2026-11-30T23:59:59
```

### 4. Soft Delete Transaction

**Endpoint:** `DELETE /api/v1/users/{userId}/transactions/{transactionId}`

Soft deletes a transaction instead of permanently removing it from the database.

**Example:**

```http
DELETE /api/v1/users/1/transactions/4
```

**Business Logic:**

- Sets the `deleted` field to `true`.
- Deleted transactions are excluded from normal transaction listings.
- A user can only delete their own transaction.
- Transaction records remain stored in the database.
- Deleting a transaction does not automatically reverse the account balance.

**Success Response:** `200 OK`

---

# 📊 Transaction Summary API

### Get Transaction Summary

**Endpoint:** `GET /api/v1/users/{userId}/transactions/summary`

Provides transaction analytics for an individual user.

**Example:**

```http
GET /api/v1/users/1/transactions/summary
```

**Sample Response:**

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

**Response Fields:**

| Field | Description |
|---|---|
| `totalCredit` | Total successful credit amount |
| `totalDebit` | Total successful debit amount |
| `currentBalance` | Current account balance |
| `totalTransactions` | Total non-deleted transactions |
| `successfulTransactions` | Number of successful transactions |
| `failedTransactions` | Number of failed transactions |
| `pendingTransactions` | Number of pending transactions |

---

# 💰 Current Balance API

### Get Current Balance

**Endpoint:** `GET /api/v1/users/{userId}/balance`

Retrieves the current account balance of a user.

**Example:**

```http
GET /api/v1/users/1/balance
```

**Sample Response:**

```json
{
  "balance": 38000.00
}
```

---

# 📈 Admin Dashboard APIs

**Base Endpoint:** `/api/admin`

### 1. Get Admin Dashboard

**Endpoint:** `GET /api/admin/dashboard`

Provides overall banking system analytics.

**Dashboard Information:**

- Total number of registered users.
- Total number of transactions.
- Total successful transaction amount.

**Example:**

```http
GET /api/admin/dashboard
```

**Sample Response:**

```json
{
  "totalUsers": 5,
  "totalTransactions": 15,
  "totalTransactionAmount": 185000.00
}
```

### 2. Admin Dashboard With Date Range

**Endpoint:** `GET /api/admin/dashboard`

Supports date-range filtering for transaction statistics.

**Example:**

```http
GET /api/admin/dashboard?fromDate=2026-09-01&toDate=2026-09-21
```

**Business Logic:**

- Total users represent all registered users.
- Transaction count includes all non-deleted transactions within the selected date range.
- Transaction amount includes only successful transactions.
- Transactions are filtered using their transaction date.

**Note:** Admin-only authorization is planned for implementation using Spring Security.

---

[//]: # ()
[//]: # (# 🌍 External Transaction Import API)

[//]: # ()
[//]: # (### Import Transactions From External API)

[//]: # ()
[//]: # (**Endpoint:** `POST /api/transactions/import`)

[//]: # ()
[//]: # (Imports transaction records from an external transaction service.)

[//]: # ()
[//]: # (**Request Body:**)

[//]: # ()
[//]: # (```json)

[//]: # ({)

[//]: # (  "userId": 1,)

[//]: # (  "url": "https://weetech-practical.free.beeceptor.com/transactions")

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (**External API:**)

[//]: # ()
[//]: # (```http)

[//]: # (GET https://weetech-practical.free.beeceptor.com/transactions)

[//]: # (```)

[//]: # ()
[//]: # (**Business Logic:**)

[//]: # ()
[//]: # (- Fetches transaction records from the external API.)

[//]: # (- Maps external transaction fields to the application's transaction entity.)

[//]: # (- Supports CREDIT and DEBIT transactions.)

[//]: # (- Skips unsupported transaction types.)

[//]: # (- Validates transaction amounts and dates.)

[//]: # (- Imported transactions are saved with `SUCCESS` status.)

[//]: # (- Imported historical transactions do not automatically modify the user's current balance.)

[//]: # ()
[//]: # (**Sample Response:**)

[//]: # ()
[//]: # (```json)

[//]: # ({)

[//]: # (  "importedCount": 2,)

[//]: # (  "skippedCount": 1,)

[//]: # (  "message": "External transactions imported successfully")

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (# ⚠️ Common HTTP Status Codes)

[//]: # ()
[//]: # (| Status Code | Description |)

[//]: # (|---|---|)

[//]: # (| `200 OK` | Request completed successfully |)

[//]: # (| `201 Created` | Resource created successfully |)

[//]: # (| `400 Bad Request` | Invalid request data |)

[//]: # (| `404 Not Found` | Requested resource not found |)

[//]: # (| `500 Internal Server Error` | Unexpected server error |)

[//]: # ()
[//]: # (---)

# 📋 Business Rules

1. Transaction amounts must be positive.
2. Users cannot debit more than their available balance.
3. CREDIT transactions increase the user's balance.
4. DEBIT transactions decrease the user's balance.
5. Failed transactions must not affect the user's balance.
6. Transactions have three statuses: `PENDING`, `SUCCESS`, and `FAILED`.
7. Soft-deleted transactions are excluded from normal transaction listings and dashboard statistics.
8. Transaction summaries calculate successful credit and debit amounts.

[//]: # (9. External transaction imports support CREDIT and DEBIT types.)

[//]: # (10. Imported historical transactions do not automatically change the current account balance.)

---

# 🔮 Future Enhancements

- [ ] Spring Security and JWT Authentication.
- [ ] Role-based authorization for ADMIN and USER.
- [ ] Global Exception Handling.
- [ ] External Transaction Import.
---

# 👨‍💻 Author

**Bank Transaction System**

Developed using:

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL

---