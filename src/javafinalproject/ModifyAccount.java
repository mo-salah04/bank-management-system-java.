/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyAccount {
    private final FileModifications fileModifications;
    private final Validations validations;

    public ModifyAccount(FileModifications fileModifications) {
        this.fileModifications = fileModifications;
        this.validations = new Validations(fileModifications); // Initialize Validations
        createModifyAccountScreen();
    }

    private void createModifyAccountScreen() {
        JFrame frame = new JFrame("Modify Account");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, frame);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel, JFrame frame) {
        panel.setLayout(null);

        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setBounds(10, 20, 150, 25);
        panel.add(accountNumberLabel);

        JTextField accountNumberField = new JTextField(20);
        accountNumberField.setBounds(150, 20, 200, 25);
        panel.add(accountNumberField);

        JButton findButton = new JButton("Find Account");
        findButton.setBounds(150, 60, 150, 25);
        panel.add(findButton);

        JLabel nameLabel = new JLabel("New Name:");
        nameLabel.setBounds(10, 100, 150, 25);
        panel.add(nameLabel);

        JTextField nameField = new JTextField(20);
        nameField.setBounds(150, 100, 200, 25);
        nameField.setEnabled(false);
        panel.add(nameField);

        JLabel mobileLabel = new JLabel("New Mobile:");
        mobileLabel.setBounds(10, 140, 150, 25);
        panel.add(mobileLabel);

        JTextField mobileField = new JTextField(20);
        mobileField.setBounds(150, 140, 200, 25);
        mobileField.setEnabled(false);
        panel.add(mobileField);

        JLabel emailLabel = new JLabel("New Email:");
        emailLabel.setBounds(10, 180, 150, 25);
        panel.add(emailLabel);

        JTextField emailField = new JTextField(20);
        emailField.setBounds(150, 180, 200, 25);
        emailField.setEnabled(false);
        panel.add(emailField);

        JButton modifyButton = new JButton("Modify Account");
        modifyButton.setBounds(150, 240, 150, 25);
        modifyButton.setEnabled(false);
        panel.add(modifyButton);

        // Find Account Logic
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountNumberField.getText().trim();
                System.out.print(accountNumberField.getText().trim());

                if (accountNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Account number is required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String accountData = fileModifications.findAccountByNumber(accountNumber);
                System.out.print(accountData);
                if (accountData == null) {
                    JOptionPane.showMessageDialog(frame, "Account not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(frame, "Account found. You can now modify the details.", "Success", JOptionPane.INFORMATION_MESSAGE);
                nameField.setEnabled(true);
                mobileField.setEnabled(true);
                emailField.setEnabled(true);
                modifyButton.setEnabled(true);
            }
        });

        // Modify Account Logic
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountNumberField.getText().trim();
                String newName = nameField.getText().trim();
                String newMobile = mobileField.getText().trim();
                String newEmail = emailField.getText().trim();

                if (newName.isEmpty() || newMobile.isEmpty() || newEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!validations.isValidName(newName)) {
                    JOptionPane.showMessageDialog(frame, "Name must contain only letters and spaces!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (validations.nameAlreadyExists(newName)) {
                    JOptionPane.showMessageDialog(frame, "An account with this name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!validations.isValidMobile(newMobile)) {
                    JOptionPane.showMessageDialog(frame, "Mobile number must be exactly 11 digits!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (validations.mobileAlreadyExists(newMobile)) {
                    JOptionPane.showMessageDialog(frame, "An account with this mobile number already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!validations.isValidEmail(newEmail)) {
                    JOptionPane.showMessageDialog(frame, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (validations.emailAlreadyExists(newEmail)) {
                    JOptionPane.showMessageDialog(frame, "An account with this email already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the account details in the file
                if (fileModifications.modifyAccount(accountNumber, newName, newMobile, newEmail)) {
                    JOptionPane.showMessageDialog(frame, "Account modified successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to modify account!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}

