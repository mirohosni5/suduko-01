package gui;

import SudokuSolutionVerifier.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SudokuGUI extends JFrame {

    private JTextField[][] cells = new JTextField[9][9];
    private ControllerFacade controller;

    // ===== MAIN =====
    public static void main(String[] args) {
        // sample empty board (you can load CSV later)
        int[][] board = new int[9][9];

        SwingUtilities.invokeLater(() -> {
            SudokuGUI gui = new SudokuGUI(board);
            gui.setVisible(true);
        });
    }

    // ===== GUI CONSTRUCTOR =====
    public SudokuGUI(int[][] board) {
        controller = new ControllerFacade(board);

        setTitle("Sudoku Verifier");
        setSize(520, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center screen
        setLayout(new BorderLayout(10, 10));

        // ===== TITLE =====
        JLabel title = new JLabel("Sudoku Verifier", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        // ===== GRID =====
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font cellFont = new Font("Arial", Font.BOLD, 16);

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                JTextField tf = new JTextField();
                tf.setHorizontalAlignment(JTextField.CENTER);
                tf.setFont(cellFont);

                // thicker borders for 3x3 boxes
                int top = (r % 3 == 0) ? 2 : 1;
                int left = (c % 3 == 0) ? 2 : 1;
                int bottom = (r == 8) ? 2 : 1;
                int right = (c == 8) ? 2 : 1;

                tf.setBorder(new LineBorder(Color.BLACK, 1));
                tf.setBorder(BorderFactory.createMatteBorder(
                        top, left, bottom, right, Color.BLACK));

                if (board[r][c] != 0) {
                    tf.setText(String.valueOf(board[r][c]));
                    tf.setEditable(false);
                    tf.setBackground(new Color(230, 230, 230));
                }

                final int row = r;
                final int col = c;

                tf.addActionListener(e -> {
                    try {
                        int value = Integer.parseInt(tf.getText());
                        if (value < 1 || value > 9) throw new Exception();
                        controller.changeCell(row, col, value);
                    } catch (Exception ex) {
                        tf.setText("");
                    }
                });

                cells[r][c] = tf;
                gridPanel.add(tf);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        // ===== BUTTONS =====
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

    // ===== VERIFY =====
    private void verifyBoard() {
        SudokuMode mode = new SequentialMode();
        ValidationResult result = mode.verify(controller.getBoard());

        if (result.isValid()) {
            JOptionPane.showMessageDialog(this, "VALID ✅");
        } else {
            JOptionPane.showMessageDialog(this, "INVALID ❌");
        }
    }

    // ===== REFRESH AFTER UNDO =====
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
}