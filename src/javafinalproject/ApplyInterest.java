/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafinalproject;

import javax.swing.*;

/**
 *
 * @author UTD
 */
public class ApplyInterest {

    private final FileModifications fileModifications;

    public ApplyInterest(FileModifications fileModifications) {
        this.fileModifications = fileModifications;
        applyInterest();
    }

    private void applyInterest() {
        boolean success = fileModifications.applyInterest();
        if (success) {
            JOptionPane.showMessageDialog(null, "Interest applied successfully to eligible Savings accounts!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No eligible accounts found for interest application.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
