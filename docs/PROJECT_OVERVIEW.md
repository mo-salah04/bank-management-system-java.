# Project Overview

## Title

Bank Management System using Java Swing

## Description

This project is a desktop banking management application developed in Java. It provides a graphical user interface for managing bank accounts and performing basic banking operations. The system uses text files to store account data, user login data, and transaction history.

## Main Workflow

1. The program starts from `JavaFinalProject.java`.
2. The login screen opens through the `Login` class.
3. After successful login, the `HomeScreen` displays the main menu.
4. The user can select operations such as adding accounts, depositing money, withdrawing money, transferring money, searching accounts, viewing accounts, applying interest, or checking transaction history.
5. The system reads and writes data through the `FileModifications` class.

## Functional Requirements

- Authenticate users using `users.txt`.
- Add new current or savings accounts.
- Prevent duplicate account data where validation requires it.
- Validate names, mobile numbers, and email formats.
- Deposit money into an account.
- Withdraw money from an account.
- Transfer money from one account to another.
- Apply interest to savings accounts.
- Search and view account data.
- Store transaction history in separate account files.

## Data Storage

The application uses text files:

- `users.txt`: stores login credentials.
- `accounts.txt`: stores account information.
- `<account-number>.txt`: stores transaction history for a specific account.

## Suggested GitHub Description

Java Swing Bank Management System with login, account management, deposits, withdrawals, transfers, interest calculation, search, and file-based storage.

## Suggested GitHub Topics

```text
java
swing
bank-management-system
file-handling
desktop-application
netbeans
academic-project
```
