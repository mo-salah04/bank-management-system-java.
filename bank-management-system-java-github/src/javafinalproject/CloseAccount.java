/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author UTD
 */
public class CloseAccount {
      private final FileModifications fileModifications;

    public CloseAccount(FileModifications fileModifications) {
        this.fileModifications = fileModifications;
        createCloseAccountScreen();
    }

    private void createCloseAccountScreen() {
        JFrame frame = new JFrame("Close Account");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);

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

        JButton closeButton = new JButton("Close Account");
        closeButton.setBounds(150, 60, 150, 25);
        panel.add(closeButton);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountNumberField.getText().trim();

                if (accountNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Account number is required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean accountDeleted = fileModifications.closeAccount(accountNumber);
                if (!accountDeleted) {
                    String accountsData = fileModifications.readAccounts();
                    String[] accounts = accountsData.split("\n");
                    for (String account : accounts) {
                        String[] details = account.split(",");
                        if (details[0].trim().equals(accountNumber)) {
                            double balance = Double.parseDouble(details[3].trim());
                            if (balance > 0) {
                                JOptionPane.showMessageDialog(frame, "Account cannot be deleted. Balance is greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(frame, "Account number not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(frame, "Account successfully closed.", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });
    }
}
