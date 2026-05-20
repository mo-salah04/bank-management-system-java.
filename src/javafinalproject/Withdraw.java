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

/**
 *
 * @author UTD
 */
public class Withdraw {
    private final FileModifications fileModifications;

    public Withdraw(FileModifications fileModifications) {
        this.fileModifications = fileModifications;
        createWithdrawScreen();
    }

    private void createWithdrawScreen() {
        JFrame frame = new JFrame("Withdraw Money");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

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

        JLabel withdrawalAmountLabel = new JLabel("Withdrawal Amount:");
        withdrawalAmountLabel.setBounds(10, 60, 150, 25);
        panel.add(withdrawalAmountLabel);

        JTextField withdrawalAmountField = new JTextField(20);
        withdrawalAmountField.setBounds(150, 60, 200, 25);
        panel.add(withdrawalAmountField);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(150, 100, 150, 25);
        panel.add(withdrawButton);

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountNumberField.getText().trim();
                String withdrawalAmountText = withdrawalAmountField.getText().trim();

                if (accountNumber.isEmpty() || withdrawalAmountText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double withdrawalAmount;
                try {
                    withdrawalAmount = Double.parseDouble(withdrawalAmountText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid withdrawal amount!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (withdrawalAmount > 10000) {
                    JOptionPane.showMessageDialog(frame, "Withdrawal amount exceeds the maximum limit of $10,000!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = fileModifications.withdrawFromAccount(accountNumber, withdrawalAmount);
                if (!success) {
                    String accountsData = fileModifications.readAccounts();
                    String[] accounts = accountsData.split("\n");
                    for (String account : accounts) {
                        String[] details = account.split(",");
                        if (details[0].trim().equals(accountNumber)) {
                            double currentBalance = Double.parseDouble(details[3].trim());
                            String accountType = details[6].trim();
                            double fee = (accountType.equalsIgnoreCase("Current") && withdrawalAmount < 1000) ? 10.0 : 0.0;

                            if (withdrawalAmount + fee > currentBalance) {
                                JOptionPane.showMessageDialog(frame, "Insufficient balance!", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(frame, "Account not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String transactionDetails = "Withdrew: $" + withdrawalAmount + " on " + new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                fileModifications.appendTransaction(accountNumber, transactionDetails);


                JOptionPane.showMessageDialog(frame, "Withdrawal successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });
    }
}
