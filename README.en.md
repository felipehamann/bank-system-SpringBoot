# 🏦 Bank API

[🇪🇸 Versión en Español](./README.md)

RESTful API developed with **Spring Boot 3** and **Java 21**, simulating the operational management of a bank.  
It allows managing customers, bank accounts, and transactions — including deposits, withdrawals, transfers, blocking, and closing accounts.  
The interactive documentation is automatically generated with **Swagger UI**.

---

## 🚀 Technologies Used

- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **Spring Validation**
- **Spring Security** *(base for future authentication)*
- **H2 Database (in-memory)**
- **Lombok**
- **Springdoc OpenAPI / Swagger UI**

---

## ⚙️ Project Structure

```
bank-api/
 ├── src/
 │   ├── main/java/com/felipehamann/bankapi/
 │   │   ├── config/              # General configuration
 │   │   ├── controller/          # REST Controllers
 │   │   ├── dto/                 # Data Transfer Objects (Request/Response)
 │   │   ├── model/               # JPA Entities (Customer, BankAccount, Transaction)
 │   │   ├── repository/          # Data access interfaces (Spring Data)
 │   │   └── service/             # Business logic and transactions
 │   └── resources/
 │       └── application.properties
 ├── pom.xml                      # Maven dependencies
 └── README.md
```

---

## 🧩 Main Entities

### 👤 Customer
Represents a bank customer.  
Fields:
- `id`
- `fullName`
- `email`

### 💳 BankAccount
Represents a customer’s bank account.  
Fields:
- `id`
- `accountNumber`
- `customer`
- `balance`
- `currency`
- `status` (ACTIVE, BLOCKED, CLOSED)

### 🔁 Transaction
Represents operations performed on an account.  
Fields:
- `id`
- `account`
- `amount`
- `type` (DEPOSIT, WITHDRAW, TRANSFER)
- `description`
- `createdAt`

---

## 🌐 Main Endpoints

### 👥 **CustomerController**
| Method | Endpoint | Description |
|---------|-----------|-------------|
| `POST` | `/api/customers` | Create a new customer |
| `GET` | `/api/customers` | Retrieve all customers |
| `GET` | `/api/customers/{id}` | Get a customer by ID |

---

### 💰 **AccountController**
| Method | Endpoint | Description |
|---------|-----------|-------------|
| `POST` | `/api/accounts` | Create a new bank account for a customer |
| `GET` | `/api/accounts/by-customer/{customerId}` | Get all accounts of a specific customer |
| `GET` | `/api/accounts/{accountNumber}` | Retrieve a specific account by number |
| `POST` | `/api/accounts/deposit` | Deposit funds into an account |
| `POST` | `/api/accounts/withdraw` | Withdraw funds from an account |
| `POST` | `/api/accounts/transfer` | Transfer money between two accounts |
| `GET` | `/api/accounts/{accountNumber}/transactions` | Get transaction history of an account |
| `PATCH` | `/api/accounts/{accountId}/status` | Change account status (ACTIVE, BLOCKED, CLOSED) |

---

## 🧪 How to Run the API

### 1️⃣ Clone the repository
```bash
git clone https://github.com/felipehamann/bank-api.git
cd bank-api
```

### 2️⃣ Build and run the app
```bash
mvn spring-boot:run
```

### 3️⃣ Access the H2 Database Console
```
http://localhost:8080/h2-console
```
- **JDBC URL:** `jdbc:h2:mem:bankdb`  
- **User:** `sa`  
- **Password:** *(empty)*

### 4️⃣ Open Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```

From Swagger UI, you can test all endpoints interactively and see the JSON responses.

---

## 🧠 Suggested Workflow

1. Create a customer (`POST /api/customers`)  
2. Create a bank account for that customer (`POST /api/accounts`)  
3. Deposit funds (`POST /api/accounts/deposit`)  
4. Withdraw or transfer funds (`POST /api/accounts/withdraw` / `POST /api/accounts/transfer`)  
5. Retrieve transactions (`GET /api/accounts/{accountNumber}/transactions`)  
6. Block or close the account (`PATCH /api/accounts/{accountId}/status`)

---

## 🧰 Key Dependencies (`pom.xml`)

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.7.0</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

---

## 🧾 Postman Example Flow

### Create Customer
```json
POST /api/customers
{
  "fullName": "Felipe Hamann",
  "email": "felipe@example.com"
}
```

### Create Account
```json
POST /api/accounts
{
  "customerId": 1,
  "currency": "CLP"
}
```

### Deposit
```json
POST /api/accounts/deposit
{
  "accountNumber": "ACC-17311899000",
  "amount": 50000
}
```

### Transfer
```json
POST /api/accounts/transfer
{
  "fromAccount": "ACC-17311899000",
  "toAccount": "ACC-17311899111",
  "amount": 10000
}
```

---

## 💾 Persistence (H2)

This project uses an **in-memory H2 database**, so all data resets when the application restarts.  
To persist data permanently, you can easily switch to **MySQL**, **PostgreSQL**, or **SQL Server** by editing the `application.properties` file.

---

## 🧑‍💻 Author

**Felipe Hamann**  
Backend Developer – Java / Spring Boot / Node.js  
📧 [felipe.hamann@sansano.usm.cl](mailto:felipe.hamann@sansano.usm.cl)  
🌐 [github.com/felipehamann](https://github.com/felipehamann)

---

> Educational project simulating the operations of a banking system, applying best practices in REST API design, security, and documentation.
