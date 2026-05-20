/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author UTD
 */
public class TransactionHistory {

    private final FileModifications fileModifications;

    public TransactionHistory(FileModifications fileModifications) {
        this.fileModifications = fileModifications;
        createTransactionHistoryScreen();
    }

    private void createTransactionHistoryScreen() {
        JFrame frame = new JFrame("Transaction History");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel);

        // Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel accountNumberLabel = new JLabel("Account Number:");
        JTextField accountNumberField = new JTextField(15);
        JButton searchButton = new JButton("View History");
        inputPanel.add(accountNumberLabel);
        inputPanel.add(accountNumberField);
        inputPanel.add(searchButton);
        panel.add(inputPanel, BorderLayout.NORTH);

        // Text Area for Transaction History
        JTextArea historyTextArea = new JTextArea();
        historyTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Back Button
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(70, 130, 180));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.setPreferredSize(new Dimension(150, 40));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> frame.dispose());

        // Search Button Action
        searchButton.addActionListener(e -> {
            String accountNumber = accountNumberField.getText().trim();
            if (accountNumber.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter an account number!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String history = fileModifications.readTransactionHistory(accountNumber);
                historyTextArea.setText(history);
            }
        });

        frame.setVisible(true);
    }
}
