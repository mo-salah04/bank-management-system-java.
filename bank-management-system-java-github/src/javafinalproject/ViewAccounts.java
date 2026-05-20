/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author UTD
 */
public class ViewAccounts {

   private final FileModifications fileModifications;

    public ViewAccounts(FileModifications fileModifications) {
        this.fileModifications = fileModifications;
        createViewAccountsScreen();
    }

    private void createViewAccountsScreen() {
        JFrame frame = new JFrame("Accounts Details");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel);

        // Title Label
        JLabel titleLabel = new JLabel("Accounts Details", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Table for Displaying Accounts
        JTable accountsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(accountsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Dropdown for Sorting Options
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel sortLabel = new JLabel("Sort by:");
        sortLabel.setFont(new Font("Arial", Font.BOLD, 14));
        String[] sortOptions = {"Name", "Balance", "Date Opened"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);
        sortPanel.add(sortLabel);
        sortPanel.add(sortComboBox);
        panel.add(sortPanel, BorderLayout.NORTH);

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

        // Load Accounts Data into Table
        ArrayList<String[]> accountsData = loadAccountsData();
        populateTable(accountsTable, accountsData);

        // Sorting Functionality
        sortComboBox.addActionListener(e -> {
            String selectedOption = (String) sortComboBox.getSelectedItem();
            if (selectedOption != null) {
                sortAccounts(accountsData, selectedOption);
                populateTable(accountsTable, accountsData);
            }
        });

        frame.setVisible(true);
    }

    private ArrayList<String[]> loadAccountsData() {
        ArrayList<String[]> accounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                accounts.add(line.split(","));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                null,
                "No accounts found or unable to load accounts data.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
        return accounts;
    }

    private void populateTable(JTable accountsTable, ArrayList<String[]> accountsData) {
        String[] columnNames = {
            "Acc No", "Name", "Balance", "Email", "Date Opened", "Tel No", "Type"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (String[] account : accountsData) {
            tableModel.addRow(account);
        }
        accountsTable.setModel(tableModel);
    }

    private void sortAccounts(ArrayList<String[]> accountsData, String sortBy) {
        switch (sortBy) {
            case "Name":
                accountsData.sort(Comparator.comparing(a -> a[1].toLowerCase())); // Sort by Name
                break;
            case "Balance":
                accountsData.sort(Comparator.comparingDouble(a -> Double.parseDouble(a[3]))); // Sort by Balance
                break;
            case "Date Opened":
                accountsData.sort(Comparator.comparing(a -> a[4])); // Sort by Date Opened
                break;
        }
    }
}
