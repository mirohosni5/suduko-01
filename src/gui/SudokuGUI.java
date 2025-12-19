package gui;

import SudokuSolutionVerifier.*;



import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SudokuGUI extends JFrame {

    private JTextField[][] cells = new JTextField[9][9];
    private ControllerFacade controller;

    // A REAL partially-filled Sudoku board (0 = empty)
    private static final int[][] START_BOARD = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},

            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},

            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    public SudokuGUI() {
        controller = new ControllerFacade(copyBoard(START_BOARD));

        setTitle("Sudoku");
        setSize(520, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Sudoku", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        add(createGrid(), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }


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
                    tf.setBackground(new Color(220, 220, 220)); // fixed cells
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

    
    private void verifyBoard() {

        
        syncBoardFromGUI();

        SudokuMode mode = new SequentialMode();
        ValidationResult result = mode.verify(controller.getBoard());

        if (hasZero(controller.getBoard())) {
            JOptionPane.showMessageDialog(this,
                    "INCOMPLETE ⚠️\nFill all cells first.");
        } else if (result.isValid()) {
            JOptionPane.showMessageDialog(this,
                    "VALID ✅");
        } else {
            JOptionPane.showMessageDialog(this,
                    "INVALID ❌\nThere are duplicates.");
        }
    }
    //helper classes that i will need
    private void syncBoardFromGUI() {
        int[][] board = controller.getBoard();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (cells[r][c].isEditable()) {
                    String text = cells[r][c].getText().trim();
                    if (text.isEmpty()) {
                        board[r][c] = 0;
                    } else {
                        try {
                            board[r][c] = Integer.parseInt(text);
                        } catch (Exception e) {
                            board[r][c] = 0;
                        }
                    }
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

    private int[][] copyBoard(int[][] src) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++)
            System.arraycopy(src[i], 0, copy[i], 0, 9);
        return copy;
    }

    
    public static void main(String[] args) {
        new SudokuGUI();
    }
}
