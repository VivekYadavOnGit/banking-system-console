# 💳 Console Bank Application (Java)

A simple **console-based banking system** built in Java using clean architecture principles.  
This project simulates real banking operations such as account creation, deposits, withdrawals, transfers, and transaction history management.

It demonstrates **OOP design**, **service-layer architecture**, and **modular code organization**.

---

## 🚀 Features

- Open new bank accounts
- Deposit money
- Withdraw money
- Transfer funds between accounts
- View account transaction statement
- List all accounts
- Search accounts by customer name
- Clean service abstraction using interfaces
- Console-based interactive UI

---

## 🧠 Tech Stack

- Java (Core Java / OOP)
- Service Layer Architecture
- Java Collections API
- Console I/O (Scanner)
- Modular package structure

---

## 📂 Project Structure

```
src/
 ├── app/
 │    └── Main.java
 ├── services/
 │    ├── BankService.java
 │    └── ServiceImpl/
 │         └── BankServiceImpl.java
 ├── models/
 │    ├── Account.java
 │    └── Transaction.java
```

- `app` → Entry point (Console UI)
- `services` → Business logic layer
- `models` → Domain entities

---

## ▶️ How to Run

### 1. Clone the repository

```
git clone https://github.com/your-username/console-bank-app.git
cd console-bank-app
```

### 2. Compile

```
javac -d out src/**/*.java
```

### 3. Run

```
java -cp out app.Main
```

---

## 🖥 Sample Console Menu

```
Welcome to Console Bank...

1) Open Account
2) Deposit
3) Withdraw
4) Transfer
5) Account Statement
6) List Accounts
7) Search Account by Name
0) Exit
```

---

## 🏗 Architecture Overview

This project follows a layered design:

```
Console UI → Service Interface → Service Implementation → Data Models
```

Benefits:

- Loose coupling
- Easy to extend
- Testable business logic
- Clean separation of concerns

---

## 📌 Learning Outcomes

This project demonstrates:

- Interface-driven design
- Encapsulation and abstraction
- Real-world domain modeling
- Transaction tracking
- Console application workflow
- Defensive input handling
- Service-oriented architecture

---

## 🔮 Future Improvements

- Persistent storage (File / Database)
- User authentication
- REST API version
- GUI interface
- Exception handling system
- Logging
- Unit tests (JUnit)

---

## 👨‍💻 Author

**Vivek Yadav**  
Java Developer | Backend Enthusiast

---

## ⭐ Support

If you like this project, consider giving it a ⭐ on GitHub!
