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
public class Login {

    private final FileModifications fileModifications;

    public Login() {
        this.fileModifications = new FileModifications();
        createLoginScreen();
    }

    private void createLoginScreen() {
        JFrame loginFrame = new JFrame("Bank Management System - Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 200);

        JPanel panel = new JPanel();
        loginFrame.add(panel);
        placeComponents(panel, loginFrame);

        loginFrame.setVisible(true);
    }

    private void placeComponents(JPanel panel, JFrame loginFrame) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(150, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 50, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 90, 80, 25);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                if (fileModifications.validateUser(username, password)) {
                    loginFrame.dispose(); // Close the login window
                    new HomeScreen(fileModifications); // Open the HomeScreen
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
