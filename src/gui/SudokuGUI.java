package gui;

import SudokuSolutionVerifier.*;

import javax.swing.*;
import java.awt.*;

public class SudokuGUI extends JFrame {

    private JTextField[][] cells = new JTextField[9][9];
    private ControllerFacade controller;

    // ================= MAIN =================
    public static void main(String[] args) {
        int[][] board = new int[9][9]; // empty board = INCOMPLETE

        SwingUtilities.invokeLater(() -> {
            SudokuGUI gui = new SudokuGUI(board);
            gui.setVisible(true);
        });
    }

    // ================= CONSTRUCTOR =================
    public SudokuGUI(int[][] board) {
        controller = new ControllerFacade(board);

        setTitle("Sudoku");
        setSize(520, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------- TITLE ----------
        JLabel title = new JLabel("Sudoku", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // ---------- GRID ----------
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font cellFont = new Font("Arial", Font.BOLD, 16);

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                JTextField tf = new JTextField();
                tf.setHorizontalAlignment(JTextField.CENTER);
                tf.setFont(cellFont);

                // thicker borders for 3x3 blocks
                int top = (r % 3 == 0) ? 2 : 1;
                int left = (c % 3 == 0) ? 2 : 1;
                int bottom = (r == 8) ? 2 : 1;
                int right = (c == 8) ? 2 : 1;

                tf.setBorder(BorderFactory.createMatteBorder(
                        top, left, bottom, right, Color.BLACK));

                final int row = r;
                final int col = c;

                tf.addActionListener(e -> {
                    try {
                        int value = Integer.parseInt(tf.getText());
                        if (value < 1 || value > 9) throw new Exception();
                        controller.changeCell(row, col, value);
                    } catch (Exception ex) {
                        tf.setText("");
                        controller.changeCell(row, col, 0);
                    }
                });

                cells[r][c] = tf;
                gridPanel.add(tf);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        // ---------- BUTTONS ----------
        JButton verifyBtn = new JButton("Verify");
        JButton undoBtn = new JButton("Undo");

        verifyBtn.setFont(new Font("Arial", Font.BOLD, 14));
        undoBtn.setFont(new Font("Arial", Font.BOLD, 14));

        verifyBtn.addActionListener(e -> verifyBoard());
        undoBtn.addActionListener(e -> {
            controller.undo();
            refreshBoard();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(verifyBtn);
        buttonPanel.add(undoBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // ================= VERIFY =================
    private void verifyBoard() {
        int[][] board = controller.getBoard();

        // INCOMPLETE check (Lab requirement)
        if (hasEmptyCell(board)) {
            JOptionPane.showMessageDialog(
                    this,
                    "INCOMPLETE ⚠️\nFill all cells first.",
                    "Result",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // VALID / INVALID check
        SudokuMode mode = new SequentialMode();
        ValidationResult result = mode.verify(board);

        if (result.isValid()) {
            JOptionPane.showMessageDialog(
                    this,
                    "VALID ✅",
                    "Result",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "INVALID ❌\nDuplicates found.",
                    "Result",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= EMPTY CHECK =================
    private boolean hasEmptyCell(int[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    // ================= REFRESH AFTER UNDO =================
    private void refreshBoard() {
        int[][] board = controller.getBoard();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                cells[r][c].setText(
                        board[r][c] == 0 ? "" : String.valueOf(board[r][c])
                );
            }
        }
    }
}
