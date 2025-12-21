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

        jLabel1 = new javax.swing.JLabel();
        easyBtn = new javax.swing.JButton();
        mediumBtn = new javax.swing.JButton();
        hardBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel1.setText("Sudoku");

        easyBtn.setText("Easy");
        mediumBtn.setText("Medium");
        hardBtn.setText("Hard");

        easyBtn.addActionListener(e -> {
            new SudokuGUI(BoardFactory.createBoard(10));
            dispose();
        });

        mediumBtn.addActionListener(e -> {
            new SudokuGUI(BoardFactory.createBoard(25));
            dispose();
        });

        hardBtn.addActionListener(e -> {
            new SudokuGUI(BoardFactory.createBoard(40));
            dispose();
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel1)
                        .addComponent(easyBtn)
                        .addComponent(mediumBtn)
                        .addComponent(hardBtn)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(30)
                        .addComponent(jLabel1)
                        .addGap(20)
                        .addComponent(easyBtn)
                        .addGap(10)
                        .addComponent(mediumBtn)
                        .addGap(10)
                        .addComponent(hardBtn)
        );

        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Start().setVisible(true));
    }

    private javax.swing.JButton easyBtn;
    private javax.swing.JButton mediumBtn;
    private javax.swing.JButton hardBtn;
    private javax.swing.JLabel jLabel1;
}
