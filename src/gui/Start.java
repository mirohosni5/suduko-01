/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

public class Start extends javax.swing.JFrame {

    public Start() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        javax.swing.JButton jButtonEasy = new javax.swing.JButton();
        javax.swing.JButton jButtonMedium = new javax.swing.JButton();
        javax.swing.JButton jButtonHard = new javax.swing.JButton();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sudoku");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel1.setText("Sudoku");

        jButtonEasy.setText("Easy");
        jButtonMedium.setText("Medium");
        jButtonHard.setText("Hard");

        // ✅ CORRECT constructor calls
        jButtonEasy.addActionListener(e -> new SudokuGUI(10));
        jButtonMedium.addActionListener(e -> new SudokuGUI(25));
        jButtonHard.addActionListener(e -> new SudokuGUI(25));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
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
        java.awt.EventQueue.invokeLater(() -> new Start().setVisible(true));
    }
}
