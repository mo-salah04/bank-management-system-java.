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
public class Deposit {
     private final FileModifications fileModifications;

    public Deposit(FileModifications fileModifications) {
        this.fileModifications = fileModifications;
        createDepositScreen();
    }

    private void createDepositScreen() {
        JFrame frame = new JFrame("Deposit Money");
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

        JLabel depositAmountLabel = new JLabel("Deposit Amount:");
        depositAmountLabel.setBounds(10, 60, 150, 25);
        panel.add(depositAmountLabel);

        JTextField depositAmountField = new JTextField(20);
        depositAmountField.setBounds(150, 60, 200, 25);
        panel.add(depositAmountField);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(150, 100, 150, 25);
        panel.add(depositButton);

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountNumberField.getText().trim();
                String depositAmountText = depositAmountField.getText().trim();

                if (accountNumber.isEmpty() || depositAmountText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double depositAmount;
                try {
                    depositAmount = Double.parseDouble(depositAmountText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid deposit amount!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (depositAmount > 10000) {
                    JOptionPane.showMessageDialog(frame, "Deposit amount exceeds the maximum limit of $10,000!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = fileModifications.depositToAccount(accountNumber, depositAmount);
                if (!success) {
                    JOptionPane.showMessageDialog(frame, "Account not found or transaction failed!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String transactionDetails = "Deposited: $" + depositAmount + " on " + new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                    fileModifications.appendTransaction(accountNumber, transactionDetails);
                    JOptionPane.showMessageDialog(frame, "Deposit successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                }
            }
        });
    }
}
