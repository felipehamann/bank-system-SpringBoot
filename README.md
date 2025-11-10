# 🏦 Bank API

API RESTful desarrollada con **Spring Boot 3** y **Java 21** que simula la gestión operativa de un banco.  
Permite administrar clientes, cuentas bancarias y transacciones, incluyendo depósitos, retiros, transferencias, bloqueos y cierres de cuenta.  
La documentación interactiva se genera automáticamente con **Swagger UI**.

---

## 🚀 Tecnologías utilizadas

- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **Spring Validation**
- **Spring Security** *(base para futuras autenticaciones)*
- **H2 Database (en memoria)**
- **Lombok**
- **Springdoc OpenAPI / Swagger UI**

---

## ⚙️ Estructura del proyecto

```
bank-api/
 ├── src/
 │   ├── main/java/com/felipehamann/bankapi/
 │   │   ├── config/              # Configuración general
 │   │   ├── controller/          # Controladores REST
 │   │   ├── dto/                 # Objetos de transferencia (Request/Response)
 │   │   ├── model/               # Entidades JPA (Customer, BankAccount, Transaction)
 │   │   ├── repository/          # Interfaces de acceso a datos (Spring Data)
 │   │   └── service/             # Lógica de negocio y transacciones
 │   └── resources/
 │       └── application.properties
 ├── pom.xml                      # Dependencias Maven
 └── README.md
```

---

## 🧩 Entidades principales

### 👤 Customer
Representa al cliente del banco.  
Campos:
- `id`
- `fullName`
- `email`

### 💳 BankAccount
Representa una cuenta bancaria vinculada a un cliente.  
Campos:
- `id`
- `accountNumber`
- `customer`
- `balance`
- `currency`
- `status` (ACTIVE, BLOCKED, CLOSED)

### 🔁 Transaction
Registra operaciones realizadas sobre una cuenta.  
Campos:
- `id`
- `account`
- `amount`
- `type` (DEPOSIT, WITHDRAW, TRANSFER)
- `description`
- `createdAt`

---

## 🌐 Endpoints principales

### 👥 **CustomerController**
| Método | Endpoint | Descripción |
|---------|-----------|-------------|
| `POST` | `/api/customers` | Crea un nuevo cliente |
| `GET` | `/api/customers` | Lista todos los clientes |
| `GET` | `/api/customers/{id}` | Obtiene un cliente por su ID |

---

### 💰 **AccountController**
| Método | Endpoint | Descripción |
|---------|-----------|-------------|
| `POST` | `/api/accounts` | Crea una nueva cuenta bancaria para un cliente |
| `GET` | `/api/accounts/by-customer/{customerId}` | Lista las cuentas de un cliente |
| `GET` | `/api/accounts/{accountNumber}` | Obtiene una cuenta por número |
| `POST` | `/api/accounts/deposit` | Realiza un depósito |
| `POST` | `/api/accounts/withdraw` | Realiza un retiro |
| `POST` | `/api/accounts/transfer` | Transfiere dinero entre cuentas |
| `GET` | `/api/accounts/{accountNumber}/transactions` | Lista los movimientos de una cuenta |
| `PATCH` | `/api/accounts/{accountId}/status` | Cambia el estado de la cuenta (ACTIVE, BLOCKED, CLOSED) |

---

## 🧪 Cómo probar la API

### 1️⃣ Clonar el repositorio
```bash
git clone https://github.com/felipehamann/bank-api.git
cd bank-api
```

### 2️⃣ Compilar y ejecutar
```bash
mvn spring-boot:run
```

### 3️⃣ Acceder a la consola H2 (base de datos en memoria)
```
http://localhost:8080/h2-console
```
- **JDBC URL:** `jdbc:h2:mem:bankdb`  
- **User:** `sa`  
- **Password:** *(vacío)*

### 4️⃣ Abrir la documentación Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```

Desde Swagger podrás ejecutar todos los endpoints directamente y visualizar las respuestas en JSON.

---

## 🧠 Flujo sugerido de uso

1. Crear cliente (`POST /api/customers`)  
2. Crear cuenta bancaria para ese cliente (`POST /api/accounts`)  
3. Depositar dinero (`POST /api/accounts/deposit`)  
4. Retirar o transferir fondos (`POST /api/accounts/withdraw` / `POST /api/accounts/transfer`)  
5. Consultar transacciones (`GET /api/accounts/{accountNumber}/transactions`)  
6. Bloquear o cerrar cuenta (`PATCH /api/accounts/{accountId}/status`)

---

## 🧰 Dependencias clave (`pom.xml`)

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

## 🧾 Ejemplo de flujo en Postman

### Crear cliente
```json
POST /api/customers
{
  "fullName": "Felipe Hamann",
  "email": "felipe@example.com"
}
```

### Crear cuenta
```json
POST /api/accounts
{
  "customerId": 1,
  "currency": "CLP"
}
```

### Depositar
```json
POST /api/accounts/deposit
{
  "accountNumber": "ACC-17311899000",
  "amount": 50000
}
```

### Transferir
```json
POST /api/accounts/transfer
{
  "fromAccount": "ACC-17311899000",
  "toAccount": "ACC-17311899111",
  "amount": 10000
}
```

---

## 💾 Persistencia en H2

El sistema utiliza una base de datos **en memoria (H2)**, por lo que todos los datos se reinician al reiniciar la aplicación.  
Si se desea persistencia real, se puede reemplazar fácilmente por **MySQL**, **PostgreSQL** o **SQL Server** modificando el `application.properties`.

---

## 🧑‍💻 Autor

**Felipe Hamann**  
Backend Developer – Java / Spring Boot / Node.js  
📧 [felipe.hamann@sansano.usm.cl](mailto:felipe.hamann@sansano.usm.cl)  
🌐 [github.com/felipehamann](https://github.com/felipehamann)

---


