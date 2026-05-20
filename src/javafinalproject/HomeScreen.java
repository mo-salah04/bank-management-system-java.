/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;


/**
 *
 * @author UTD
 */
public class HomeScreen {

    private final FileModifications fileModifications;

    public HomeScreen(FileModifications fileModifications) {
        this.fileModifications = fileModifications;
        createHomeScreen();
    }

    private void createHomeScreen() {
        JFrame homeFrame = new JFrame("Bank Management System - Home");
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setSize(600, 600);

        JPanel panel = new JPanel();
        homeFrame.add(panel);
        panel.setLayout(null);

        // Group 1: Account Management (Blue)
        JButton addAccount = new JButton("Add New Account");
        addAccount.setBounds(100, 50, 200, 25);
        addAccount.setBackground(new Color(70, 130, 180)); // Steel Blue
        addAccount.setForeground(Color.WHITE);
        panel.add(addAccount);

        JButton modifyEmailButton = new JButton("Modify Email");
        modifyEmailButton.setBounds(100, 100, 200, 25);
        modifyEmailButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        modifyEmailButton.setForeground(Color.WHITE);
        panel.add(modifyEmailButton);

        JButton closeAccountButton = new JButton("Close Account");
        closeAccountButton.setBounds(100, 150, 200, 25);
        closeAccountButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        closeAccountButton.setForeground(Color.WHITE);
        panel.add(closeAccountButton);

        // Group 2: Transactions (Green)
        JButton depositButton = new JButton("Deposit Money");
        depositButton.setBounds(100, 200, 200, 25);
        depositButton.setBackground(new Color(60, 179, 113)); // Medium Sea Green
        depositButton.setForeground(Color.WHITE);
        panel.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw Money");
        withdrawButton.setBounds(100, 250, 200, 25);
        withdrawButton.setBackground(new Color(60, 179, 113)); // Medium Sea Green
        withdrawButton.setForeground(Color.WHITE);
        panel.add(withdrawButton);

        JButton transferButton = new JButton("Transfer Money");
        transferButton.setBounds(100, 300, 200, 25);
        transferButton.setBackground(new Color(60, 179, 113)); // Medium Sea Green
        transferButton.setForeground(Color.WHITE);
        panel.add(transferButton);

        // Group 3: Utilities (Orange)
        JButton interestButton = new JButton("Apply Interest");
        interestButton.setBounds(100, 350, 200, 25);
        interestButton.setBackground(new Color(255, 165, 0)); // Orange
        interestButton.setForeground(Color.WHITE);
        panel.add(interestButton);

        JButton searchAccountsButton = new JButton("Search Accounts");
        searchAccountsButton.setBounds(100, 400, 200, 25);
        searchAccountsButton.setBackground(new Color(255, 165, 0)); // Orange
        searchAccountsButton.setForeground(Color.WHITE);
        panel.add(searchAccountsButton);

        JButton viewAccountsButton = new JButton("View Accounts");
        viewAccountsButton.setBounds(100, 450, 200, 25);
        viewAccountsButton.setBackground(new Color(255, 165, 0)); // Orange
        viewAccountsButton.setForeground(Color.WHITE);
        panel.add(viewAccountsButton);

        JButton transactionHistoryButton = new JButton("Transaction History");
        transactionHistoryButton.setBounds(100, 500, 200, 25);
        transactionHistoryButton.setBackground(new Color(255, 165, 0)); // Orange
        transactionHistoryButton.setForeground(Color.WHITE);
        panel.add(transactionHistoryButton);

        // Logout button (Red)
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(400, 300, 100, 25);
        logoutButton.setBackground(new Color(220, 20, 60)); // Crimson
        logoutButton.setForeground(Color.WHITE);
        panel.add(logoutButton);

        // Action listeners
        addAccount.addActionListener(e -> new AddNewAccount(fileModifications));
        modifyEmailButton.addActionListener(e -> new ModifyAccount(fileModifications));
        closeAccountButton.addActionListener(e -> new CloseAccount(fileModifications));
        depositButton.addActionListener(e -> new Deposit(fileModifications));
        withdrawButton.addActionListener(e -> new Withdraw(fileModifications));
        transferButton.addActionListener(e -> new Transfer(fileModifications));
        interestButton.addActionListener(e -> new ApplyInterest(fileModifications));
        searchAccountsButton.addActionListener(e -> new SearchAccounts(fileModifications));
        viewAccountsButton.addActionListener(e -> new ViewAccounts(fileModifications));
        transactionHistoryButton.addActionListener(e -> new TransactionHistory(fileModifications));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeFrame.dispose(); // Close the home screen
                new Login(); // Open the login screen
            }
        });

        homeFrame.setVisible(true);
    }
}
