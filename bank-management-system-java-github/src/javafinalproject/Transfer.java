/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author UTD
 */
public class Transfer {

    private final FileModifications fileModifications;

    public Transfer(FileModifications fileModifications) {
        this.fileModifications = fileModifications;
        createTransferScreen();
    }

    private void createTransferScreen() {
        JFrame frame = new JFrame("Transfer Money");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, frame);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel, JFrame frame) {
        panel.setLayout(null);

        JLabel senderAccountLabel = new JLabel("Sender Account:");
        senderAccountLabel.setBounds(10, 20, 150, 25);
        panel.add(senderAccountLabel);

        JTextField senderAccountField = new JTextField(20);
        senderAccountField.setBounds(150, 20, 200, 25);
        panel.add(senderAccountField);

        JLabel receiverAccountLabel = new JLabel("Receiver Account:");
        receiverAccountLabel.setBounds(10, 60, 150, 25);
        panel.add(receiverAccountLabel);

        JTextField receiverAccountField = new JTextField(20);
        receiverAccountField.setBounds(150, 60, 200, 25);
        panel.add(receiverAccountField);

        JLabel transferAmountLabel = new JLabel("Transfer Amount:");
        transferAmountLabel.setBounds(10, 100, 150, 25);
        panel.add(transferAmountLabel);

        JTextField transferAmountField = new JTextField(20);
        transferAmountField.setBounds(150, 100, 200, 25);
        panel.add(transferAmountField);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(150, 140, 150, 25);
        panel.add(transferButton);

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String senderAccountNumber = senderAccountField.getText().trim();
                String receiverAccountNumber = receiverAccountField.getText().trim();
                String transferAmountText = transferAmountField.getText().trim();

                if (senderAccountNumber.isEmpty() || receiverAccountNumber.isEmpty() || transferAmountText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double transferAmount;
                try {
                    transferAmount = Double.parseDouble(transferAmountText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid transfer amount!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (transferAmount <= 0) {
                    JOptionPane.showMessageDialog(frame, "Transfer amount must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = fileModifications.transferBetweenAccounts(senderAccountNumber, receiverAccountNumber, transferAmount);
                if (!success) {
                    JOptionPane.showMessageDialog(frame, "Transfer failed! Check account numbers or balance.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                    fileModifications.appendTransaction(senderAccountNumber, "Transferred: $" + transferAmount + " to " + receiverAccountNumber + " on " + date);
                    fileModifications.appendTransaction(receiverAccountNumber, "Received: $" + transferAmount + " from " + senderAccountNumber + " on " + date);
                    JOptionPane.showMessageDialog(frame, "Transfer successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                }
            }
        });
    }
}
