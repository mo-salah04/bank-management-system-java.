package javafinalproject;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileModifications {

    private final String usersFile = "users.txt";
    private final String accountsFile = "accounts.txt";

    // Validate user credentials from the users file
    public boolean validateUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(" ");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Save account data to the accounts file
    public void saveAccount(String accountData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(accountsFile, true))) {
            writer.write(accountData);
            writer.newLine(); // Ensure each account is written on a new line
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read all accounts from the file
    public String readAccounts() {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + accountsFile);
            createFileIfNotExists(accountsFile); // Ensure the file exists
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toString().trim(); // Remove extra newlines
    }

    // Find an account by account number
    public String findAccountByNumber(String accountNumber) {
        String accountsData = readAccounts();
        String[] accounts = accountsData.split("\n");
        for (String account : accounts) {
            String[] details = account.split(",");
            if (details.length > 0 && details[0].trim().equals(accountNumber)) {
                return account; // Return the account details
            }
        }
        return null; // Account not found
    }

    // Modify account details in the file
    public boolean modifyAccount(String accountNumber, String newName, String newMobile, String newEmail) {
        String accountsData = readAccounts();
        String[] accounts = accountsData.split("\n");
        StringBuilder updatedData = new StringBuilder();

        boolean modified = false;

        for (String account : accounts) {
            String[] details = account.split(",");
            if (details.length > 0 && details[0].trim().equals(accountNumber)) {
                // Modify the account details
                details[1] = newName; // Name
                details[2] = newEmail; // Email
                details[4] = newMobile; // Mobile
                modified = true;
            }
            updatedData.append(String.join(",", details)).append("\n");
        }

        if (modified) {
            writeAccounts(updatedData.toString()); // Save updated data without the deleted account
        }

        return modified;
    }

    // Write updated accounts data to the file
    private void writeAccounts(String updatedData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(accountsFile))) {
            writer.write(updatedData.trim()); // Remove extra newlines
            writer.newLine(); // Ensure a proper newline at the end
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Delete an account if it exists and has a zero balance
    public boolean closeAccount(String accountNumber) {
        String accountsData = readAccounts();
        String[] accounts = accountsData.split("\n");
        StringBuilder updatedData = new StringBuilder();

        boolean accountFound = false;
        boolean deletionAllowed = false;

        for (String account : accounts) {
            String[] details = account.split(",");
            if (details.length > 0 && details[0].trim().equals(accountNumber)) {
                accountFound = true;
                double balance = Double.parseDouble(details[3].trim()); // Parse balance
                if (balance > 0) {
                    deletionAllowed = false; // Account has non-zero balance
                    break;
                } else {
                    deletionAllowed = true; // Account has zero balance
                    continue; // Skip this account (delete it)
                }
            }
            updatedData.append(account).append("\n");
        }

        if (accountFound && deletionAllowed) {
            writeAccounts(updatedData.toString()); // Save updated data without the deleted account
            return true; // Account successfully deleted
        }

        return accountFound ? deletionAllowed : false; // Return false if not found or not allowed
    }

    // Create a file if it does not exist
    private void createFileIfNotExists(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // File is created if it does not exist
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Deposit money to the account if the amount is valid
    public boolean depositToAccount(String accountNumber, double depositAmount) {
        if (depositAmount > 10000) {
            System.err.println("Deposit exceeds the maximum limit of $10,000");
            return false; // Deposit exceeds the maximum limit
        }

        String accountsData = readAccounts();
        String[] accounts = accountsData.split("\n");
        StringBuilder updatedData = new StringBuilder();

        boolean accountFound = false;

        for (String account : accounts) {
            String[] details = account.split(",");
            if (details.length > 0 && details[0].trim().equals(accountNumber)) {
                accountFound = true;
                double currentBalance = Double.parseDouble(details[3].trim());
                double newBalance = currentBalance + depositAmount;
                details[3] = String.format("%.2f", newBalance); // Update the balance and format to 2 decimal places
            }
            updatedData.append(String.join(",", details)).append("\n");
        }

        if (accountFound) {
            writeAccounts(updatedData.toString()); // Save updated data with the new balance
        } else {
            System.err.println("Account not found for the given account number: " + accountNumber);
        }

        return accountFound;
    }

// Withdraw money from the account if the amount is valid
    public boolean withdrawFromAccount(String accountNumber, double withdrawalAmount) {
        if (withdrawalAmount > 10000) {
            return false; // Exceeds maximum withdrawal limit
        }

        String accountsData = readAccounts();
        String[] accounts = accountsData.split("\n");
        StringBuilder updatedData = new StringBuilder();

        boolean accountFound = false;

        for (String account : accounts) {
            String[] details = account.split(",");
            if (details.length > 0 && details[0].trim().equals(accountNumber)) {
                accountFound = true;

                double currentBalance = Double.parseDouble(details[3].trim());
                String accountType = details[6].trim();

                double fee = 0.0;
                if (accountType.equalsIgnoreCase("Current") && withdrawalAmount < 1000) {
                    fee = 10.0; // Apply $10 fee for withdrawals under $1,000 for Current accounts
                }

                if (withdrawalAmount + fee > currentBalance) {
                    return false; // Insufficient balance
                }

                double newBalance = currentBalance - withdrawalAmount - fee;
                details[3] = String.valueOf(newBalance); // Update the balance
            }
            updatedData.append(String.join(",", details)).append("\n");
        }

        if (accountFound) {
            writeAccounts(updatedData.toString()); // Save updated data to the file
        }

        return accountFound;
    }

// Transfer money between accounts
    public boolean transferBetweenAccounts(String senderAccountNumber, String receiverAccountNumber, double transferAmount) {
        if (transferAmount <= 0) {
            return false; // Invalid transfer amount
        }

        String accountsData = readAccounts();
        String[] accounts = accountsData.split("\n");
        StringBuilder updatedData = new StringBuilder();

        boolean senderFound = false;
        boolean receiverFound = false;
        boolean transferSuccessful = false;

        double senderFee = 0.0;

        for (String account : accounts) {
            String[] details = account.split(",");
            if (details.length > 0) {
                if (details[0].trim().equals(senderAccountNumber)) {
                    senderFound = true;
                    double senderBalance = Double.parseDouble(details[3].trim());
                    String senderAccountType = details[6].trim();

                    // Apply $10 fee for Current Accounts
                    if (senderAccountType.equalsIgnoreCase("Current")) {
                        senderFee = 10.0;
                    }

                    if (transferAmount + senderFee > senderBalance) {
                        return false; // Insufficient balance
                    }

                    double newSenderBalance = senderBalance - transferAmount - senderFee;
                    details[3] = String.valueOf(newSenderBalance); // Update sender balance
                    transferSuccessful = true;
                } else if (details[0].trim().equals(receiverAccountNumber)) {
                    receiverFound = true;
                    double receiverBalance = Double.parseDouble(details[3].trim());
                    double newReceiverBalance = receiverBalance + transferAmount;
                    details[3] = String.valueOf(newReceiverBalance); // Update receiver balance
                }
            }
            updatedData.append(String.join(",", details)).append("\n");
        }

        if (senderFound && receiverFound && transferSuccessful) {
            writeAccounts(updatedData.toString()); // Save updated balances
            return true; // Transfer successful
        }

        return false; // Transfer failed
    }

    public boolean applyInterest() {
        String accountsData = readAccounts();
        String[] accounts = accountsData.split("\n");
        StringBuilder updatedData = new StringBuilder();

        boolean interestApplied = false;

        for (String account : accounts) {
            String[] details = account.split(",");
            if (details.length > 6 && details[6].trim().equalsIgnoreCase("Savings")) {
                String dateOpened = details[5].trim();
                try {
                    Date openedDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateOpened);
                    Date currentDate = new Date();

                    // Check if the account has been active for 4 months or longer
                    long diff = currentDate.getTime() - openedDate.getTime();
                    long monthsActive = diff / (1000L * 60 * 60 * 24 * 30);

                    if (monthsActive >= 4) {
                        double currentBalance = Double.parseDouble(details[3].trim());
                        double newBalance = currentBalance * 1.05; // Apply 5% interest
                        details[3] = String.valueOf(newBalance); // Update balance
                        interestApplied = true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            updatedData.append(String.join(",", details)).append("\n");
        }

        if (interestApplied) {
            writeAccounts(updatedData.toString()); // Update accounts.txt
        }

        return interestApplied;
    }

  // Create a transaction history file for a new account
public void createTransactionHistoryFile(String accountNumber) {
    String fileName = accountNumber + ".txt";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        writer.write("Transaction History for Account: " + accountNumber + "\n");
        writer.write("--------------------------------------------------\n");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// Append a transaction record to the transaction history file
public void appendTransaction(String accountNumber, String transactionDetails) {
    String fileName = accountNumber + ".txt";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
        writer.write(transactionDetails + "\n");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// Read the transaction history file for an account
public String readTransactionHistory(String accountNumber) {
    String fileName = accountNumber + ".txt";
    StringBuilder history = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = reader.readLine()) != null) {
            history.append(line).append("\n");
        }
    } catch (FileNotFoundException e) {
        return "No transaction history found for account: " + accountNumber;
    } catch (IOException e) {
        e.printStackTrace();
    }
    return history.toString();
}
}
