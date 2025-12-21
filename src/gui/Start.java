/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import javax.swing.*;

public class Start extends javax.swing.JFrame {

    public Start() {
        initComponents();
        setLocationRelativeTo(null); // center window
    }

    private void initComponents() {

        JButton jButtonEasy = new JButton();
        JButton jButtonMedium = new JButton();
        JButton jButtonHard = new JButton();
        JLabel jLabel1 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sudoku");

        // ===== TITLE =====
        jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        jLabel1.setText("Sudoku");

        // ===== BUTTON TEXT =====
        jButtonEasy.setText("Easy");
        jButtonMedium.setText("Medium");
        jButtonHard.setText("Hard");

        // ===== BUTTON ACTIONS =====
        // Easy → remove 10 cells
        jButtonEasy.addActionListener(e -> new SudokuGUI(10));

        // Medium → remove 25 cells
        jButtonMedium.addActionListener(e -> new SudokuGUI(25));

        // Hard → remove 25 cells (as required in PDF)
        jButtonHard.addActionListener(e -> new SudokuGUI(25));

        // ===== LAYOUT =====
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel1)
                        .addComponent(jButtonEasy, 120, 120, 120)
                        .addComponent(jButtonMedium, 120, 120, 120)
                        .addComponent(jButtonHard, 120, 120, 120)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(20)
                        .addComponent(jLabel1)
                        .addGap(30)
                        .addComponent(jButtonEasy)
                        .addGap(15)
                        .addComponent(jButtonMedium)
                        .addGap(15)
                        .addComponent(jButtonHard)
                        .addGap(30)
        );

        pack();
    }

    public static void main(String[] args) {

        // ===== NIMBUS LOOK & FEEL (RESTORED) =====
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // fallback to default
        }

        // ===== START APP =====
        java.awt.EventQueue.invokeLater(() -> new Start().setVisible(true));
    }
}
