# Bank Management System - Java Swing

A desktop **Bank Management System** built using **Java Swing**. The application stores account and user data in text files and provides a graphical interface for common banking operations.

## Features

- User login screen
- Add a new bank account
- Modify account email
- Close an account
- Deposit money
- Withdraw money
- Transfer money between accounts
- Apply interest to eligible savings accounts
- Search accounts
- View all accounts
- View transaction history
- File-based data storage using `.txt` files

## Technologies Used

- Java
- Java Swing GUI
- NetBeans project structure
- Text-file data storage

## Project Structure

```text
bank-management-system-java/
├── src/
│   └── javafinalproject/
│       ├── JavaFinalProject.java
│       ├── Login.java
│       ├── HomeScreen.java
│       ├── AddNewAccount.java
│       ├── ModifyAccount.java
│       ├── CloseAccount.java
│       ├── Deposit.java
│       ├── Withdraw.java
│       ├── Transfer.java
│       ├── ApplyInterest.java
│       ├── SearchAccounts.java
│       ├── ViewAccounts.java
│       ├── TransactionHistory.java
│       ├── FileModifications.java
│       └── Validations.java
├── accounts.txt
├── users.txt
├── docs/
│   └── PROJECT_OVERVIEW.md
├── nbproject/
├── build.xml
├── manifest.mf
├── .gitignore
└── README.md
```

## Main Classes

| Class | Purpose |
|---|---|
| `JavaFinalProject` | Main entry point of the application. |
| `Login` | Handles user login. |
| `HomeScreen` | Main menu/dashboard after login. |
| `AddNewAccount` | Creates new bank accounts. |
| `ModifyAccount` | Modifies account email information. |
| `CloseAccount` | Closes accounts when allowed. |
| `Deposit` | Handles deposit transactions. |
| `Withdraw` | Handles withdrawal transactions. |
| `Transfer` | Transfers money between two accounts. |
| `ApplyInterest` | Applies interest to eligible savings accounts. |
| `SearchAccounts` | Searches account records. |
| `ViewAccounts` | Displays existing accounts. |
| `TransactionHistory` | Shows transaction history for accounts. |
| `FileModifications` | Handles reading/writing/updating text files. |
| `Validations` | Validates names, emails, mobile numbers, and duplicate data. |

## How to Run

### Option 1: Run in NetBeans

1. Open NetBeans.
2. Click **File > Open Project**.
3. Select this project folder.
4. Right-click the project and choose **Run**.

### Option 2: Run from Terminal

From the project root:

```bash
javac -d build/classes src/javafinalproject/*.java
java -cp build/classes javafinalproject.JavaFinalProject
```

## Default Login Data

The file `users.txt` contains sample login credentials in this format:

```text
username password
```

Example:

```text
Omar test123
```

## Data Files

- `accounts.txt` stores account records.
- Individual account-number `.txt` files store transaction history.
- `users.txt` stores usernames and passwords for login.

## Notes

This is an academic Java project using simple text-file storage instead of a database. It is suitable for demonstrating Java GUI programming, file handling, validation, and basic banking operations.
