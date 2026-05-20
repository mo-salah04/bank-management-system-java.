/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;


/**
 *
 * @author UTD
 */
public class Validations {
 private final FileModifications fileModifications;

    public Validations(FileModifications fileModifications) {
        this.fileModifications = fileModifications;
    }

    // Validate name: unique, no numbers, not repeated
    public boolean nameAlreadyExists(String name) {
        String accountsData = fileModifications.readAccounts();
        String[] accounts = accountsData.split("\\n");
        for (String account : accounts) {
            String[] details = account.split(",");
            if (details.length > 1 && details[1].trim().equalsIgnoreCase(name.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidName(String name) {
        return name.matches("^[a-zA-Z\\s]+$"); // Only letters and spaces
    }

    // Validate mobile: unique, 11 digits, no letters
    public boolean mobileAlreadyExists(String mobile) {
        String accountsData = fileModifications.readAccounts();
        String[] accounts = accountsData.split("\\n");
        for (String account : accounts) {
            String[] details = account.split(",");
            if (details.length > 4 && details[4].trim().equals(mobile.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidMobile(String mobile) {
        return mobile.matches("\\d{11}"); // Exactly 11 digits
    }

    // Validate email: unique, no spaces, valid format
    public boolean emailAlreadyExists(String email) {
        String accountsData = fileModifications.readAccounts();
        String[] accounts = accountsData.split("\\n");
        for (String account : accounts) {
            String[] details = account.split(",");
            if (details.length > 2 && details[2].trim().equalsIgnoreCase(email.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidEmail(String email) {
        return email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"); // Valid email format, no spaces
    }
}
