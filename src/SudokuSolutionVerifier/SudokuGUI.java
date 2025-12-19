package SudokuSolutionVerifier;

import javax.swing.*;
import java.awt.*;

public class SudokuGUI extends JFrame {

    private JTextField[][] cells = new JTextField[9][9];
    private ControllerFacade controller;

    public SudokuGUI(int[][] board) {
        controller = new ControllerFacade(board);

        setTitle("Sudoku Verifier");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Sudoku Grid =====
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                JTextField tf = new JTextField();
                tf.setHorizontalAlignment(JTextField.CENTER);

                // show initial board values
                if (board[r][c] != 0) {
                    tf.setText(String.valueOf(board[r][c]));
                }

                int row = r;
                int col = c;

                // when user edits a cell
                tf.addActionListener(e -> {
                    try {
                        int val = Integer.parseInt(tf.getText());
                        controller.changeCell(row, col, val);
                    } catch (Exception ex) {
                        tf.setText("");
                        controller.changeCell(row, col, 0);
                    }
                });

                cells[r][c] = tf;
                gridPanel.add(tf);
            }
        }

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel();

        JButton verifyBtn = new JButton("Verify");
        JButton undoBtn = new JButton("Undo");

        // Verify button logic
        verifyBtn.addActionListener(e -> {
            SudokuMode mode = new SequentialMode();
            ValidationResult result = mode.verify(controller.getBoard());

            if (result.isValid()) {
                JOptionPane.showMessageDialog(this, "VALID ✅");
            } else {
                JOptionPane.showMessageDialog(this, "INVALID ❌");
            }
        });

        // Undo button logic
        undoBtn.addActionListener(e -> {
            controller.undo();
            refreshBoard();
        });

        buttonPanel.add(verifyBtn);
        buttonPanel.add(undoBtn);

        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // refresh GUI from board
    private void refreshBoard() {
        int[][] board = controller.getBoard();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) {
                    cells[r][c].setText("");
                } else {
                    cells[r][c].setText(String.valueOf(board[r][c]));
                }
            }
        }
    }

    // ===== Main method =====
    public static void main(String[] args) {

        int[][] emptyBoard = new int[9][9]; // all zeros

        SwingUtilities.invokeLater(() -> {
            new SudokuGUI(emptyBoard).setVisible(true);
        });
    }
}
