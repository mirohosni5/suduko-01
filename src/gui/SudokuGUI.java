package gui;

import SudokuSolutionVerifier.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SudokuGUI extends JFrame {

    private JTextField[][] cells = new JTextField[9][9];
    private ControllerFacade controller;

    // ✅ Constructor RECEIVES a 9x9 board
    public SudokuGUI(int[][] board) {
        controller = new ControllerFacade(copyBoard(board));

        setTitle("Sudoku");
        setSize(520, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Sudoku", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        add(createGrid(), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ================= GRID =================
    private JPanel createGrid() {
        JPanel panel = new JPanel(new GridLayout(9, 9));
        panel.setBorder(new LineBorder(Color.BLACK, 2));

        int[][] board = controller.getBoard();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                JTextField tf = new JTextField();
                tf.setHorizontalAlignment(JTextField.CENTER);
                tf.setFont(new Font("Arial", Font.BOLD, 16));
                tf.setBorder(new LineBorder(Color.GRAY));

                if (board[r][c] != 0) {
                    tf.setText(String.valueOf(board[r][c]));
                    tf.setEditable(false);
                    tf.setBackground(new Color(220, 220, 220));
                }

                final int row = r;
                final int col = c;

                tf.addActionListener(e -> {
                    try {
                        int val = Integer.parseInt(tf.getText());
                        if (val < 1 || val > 9) throw new Exception();
                        controller.changeCell(row, col, val);
                    } catch (Exception ex) {
                        tf.setText("");
                    }
                });

                cells[r][c] = tf;
                panel.add(tf);
            }
        }
        return panel;
    }

    // ================= BUTTONS =================
    private JPanel createButtons() {
        JPanel panel = new JPanel();

        JButton verifyBtn = new JButton("Verify");
        JButton undoBtn = new JButton("Undo");

        verifyBtn.addActionListener(e -> verifyBoard());
        undoBtn.addActionListener(e -> {
            controller.undo();
            refreshBoard();
        });

        panel.add(verifyBtn);
        panel.add(undoBtn);
        return panel;
    }

    // ================= VERIFY =================
    private void verifyBoard() {
        syncBoardFromGUI();

        SudokuMode mode = new SequentialMode();
        ValidationResult result = mode.verify(controller.getBoard());

        if (hasZero(controller.getBoard())) {
            JOptionPane.showMessageDialog(this,
                    "INCOMPLETE ⚠️\nFill all cells first.");
        } else if (result.isValid()) {
            JOptionPane.showMessageDialog(this, "VALID ✅");
        } else {
            JOptionPane.showMessageDialog(this,
                    "INVALID ❌\nThere are duplicates.");
        }
    }

    // ================= HELPERS =================
    private void syncBoardFromGUI() {
        int[][] board = controller.getBoard();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (cells[r][c].isEditable()) {
                    String text = cells[r][c].getText().trim();
                    board[r][c] = text.isEmpty() ? 0 : Integer.parseInt(text);
                }
            }
        }
    }

    private void refreshBoard() {
        int[][] board = controller.getBoard();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (cells[r][c].isEditable()) {
                    cells[r][c].setText(
                            board[r][c] == 0 ? "" : String.valueOf(board[r][c])
                    );
                }
            }
        }
    }

    private boolean hasZero(int[][] board) {
        for (int[] row : board)
            for (int v : row)
                if (v == 0) return true;
        return false;
    }

    // ✅ FIXED: src is int[][] now
    private int[][] copyBoard(int[][] src) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(src[i], 0, copy[i], 0, 9);
        }
        return copy;
    }
}
