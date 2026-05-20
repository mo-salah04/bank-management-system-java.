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

/**
 *
 * @author UTD
 */
public class SearchAccounts {

    private final FileModifications fileModifications;

    public SearchAccounts(FileModifications fileModifications) {
        this.fileModifications = fileModifications;
        createSearchAccountsScreen();
    }

    private void createSearchAccountsScreen() {
        JFrame frame = new JFrame("Search Accounts");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Name:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        panel.add(searchPanel, BorderLayout.NORTH);

        // Table for Search Results
        JTable resultsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Back Button
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(70, 130, 180)); // Steel Blue background
        backButton.setForeground(Color.WHITE); // White text
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.setPreferredSize(new Dimension(150, 40));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Align to bottom-right
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> frame.dispose());

        // Search Button Action
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a keyword to search!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                loadSearchResults(keyword, resultsTable);
            }
        });

        frame.setVisible(true);
    }

    private void loadSearchResults(String keyword, JTable resultsTable) {
        String[] columnNames = {
            "Acc No", "Name", "Balance", "Email", "Date Opened", "Tel No", "Type"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        resultsTable.setModel(tableModel);

        boolean matchFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] accountDetails = line.split(",");
                if (accountDetails.length > 1 && accountDetails[1].toLowerCase().contains(keyword.toLowerCase())) {
                    tableModel.addRow(accountDetails);
                    matchFound = true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error loading accounts data.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!matchFound) {
            JOptionPane.showMessageDialog(null, "No matches found for the keyword: " + keyword, "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
