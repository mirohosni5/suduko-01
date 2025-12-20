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

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Easy");
        jButton2.setText("Medium");
        jButton3.setText("Hard");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jLabel1.setText("Sudoku");

        // ================= BUTTON ACTIONS =================

        jButton1.addActionListener(e -> {
            new SudokuGUI(GameGenerator.generate(10)); // Easy
        });

        jButton2.addActionListener(e -> {
            new SudokuGUI(GameGenerator.generate(25)); // Medium
        });

        jButton3.addActionListener(e -> {
            new SudokuGUI(GameGenerator.generate(25)); // Hard
        });

        javax.swing.GroupLayout layout =
                new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel1)
                        .addComponent(jButton1)
                        .addComponent(jButton2)
                        .addComponent(jButton3)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(20)
                        .addComponent(jLabel1)
                        .addGap(20)
                        .addComponent(jButton1)
                        .addGap(10)
                        .addComponent(jButton2)
                        .addGap(10)
                        .addComponent(jButton3)
                        .addGap(20)
        );

        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Start().setVisible(true));
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
}
