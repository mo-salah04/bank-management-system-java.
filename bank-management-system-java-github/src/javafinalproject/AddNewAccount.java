/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author UTD
 */
public class AddNewAccount {

    private final FileModifications fileModifications;
    private final Validations validations;

    public AddNewAccount(FileModifications fileModifications) {
        this.fileModifications = fileModifications;
        this.validations = new Validations(fileModifications); // Initialize Validations
        createAddAccountScreen();
    }

    private void createAddAccountScreen() {
        JFrame frame = new JFrame("Add New Account");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, frame);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel, JFrame frame) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 20, 100, 25);
        panel.add(nameLabel);

        JTextField nameField = new JTextField(20);
        nameField.setBounds(150, 20, 200, 25);
        panel.add(nameField);

        JLabel mobileLabel = new JLabel("Mobile:");
        mobileLabel.setBounds(10, 60, 100, 25);
        panel.add(mobileLabel);

        JTextField mobileField = new JTextField(20);
        mobileField.setBounds(150, 60, 200, 25);
        panel.add(mobileField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 100, 100, 25);
        panel.add(emailLabel);

        JTextField emailField = new JTextField(20);
        emailField.setBounds(150, 100, 200, 25);
        panel.add(emailField);

        JLabel typeLabel = new JLabel("Account Type:");
        typeLabel.setBounds(10, 140, 100, 25);
        panel.add(typeLabel);

        String[] accountTypes = {"Current", "Savings"};
        JComboBox<String> accountTypeCombo = new JComboBox<>(accountTypes);
        accountTypeCombo.setBounds(150, 140, 200, 25);
        panel.add(accountTypeCombo);

        JButton addButton = new JButton("Add Account");
        addButton.setBounds(150, 200, 150, 25);
        panel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String mobile = mobileField.getText().trim();
                String email = emailField.getText().trim();
                String accountType = accountTypeCombo.getSelectedItem().toString();

                // Validation checks
                if (name.isEmpty() || mobile.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!validations.isValidName(name)) {
                    JOptionPane.showMessageDialog(frame, "Name must contain only letters and spaces!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (validations.nameAlreadyExists(name)) {
                    JOptionPane.showMessageDialog(frame, "An account with this name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!validations.isValidMobile(mobile)) {
                    JOptionPane.showMessageDialog(frame, "Mobile number must be exactly 11 digits!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (validations.mobileAlreadyExists(mobile)) {
                    JOptionPane.showMessageDialog(frame, "An account with this mobile number already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!validations.isValidEmail(email)) {
                    JOptionPane.showMessageDialog(frame, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (validations.emailAlreadyExists(email)) {
                    JOptionPane.showMessageDialog(frame, "An account with this email already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Generate unique account number
                String accountNumber = generateUniqueAccountNumber();

                // Get current date and time
                String dateOpened = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
                String initialBalance = "0";

                // Save account data
                String accountData = String.join(",", accountNumber, name, email, initialBalance, mobile, dateOpened, accountType);
                fileModifications.saveAccount(accountData);
                // Create transaction history file for the new account
                fileModifications.createTransactionHistoryFile(accountNumber);

                JOptionPane.showMessageDialog(frame, "Account added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                frame.dispose();
            }
        });
    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        Random random = new Random();
        do {
            accountNumber = String.format("%010d", random.nextInt(1_000_000_000));
        } while (!isAccountNumberUnique(accountNumber));
        return accountNumber;
    }

    private boolean isAccountNumberUnique(String accountNumber) {
        String accountsData = fileModifications.readAccounts();
        String[] accounts = accountsData.split("\\n");
        for (String account : accounts) {
            String[] details = account.split(",");
            if (details.length > 0 && details[0].trim().equals(accountNumber)) {
                return false;
            }
        }
        return true;
    }
}
