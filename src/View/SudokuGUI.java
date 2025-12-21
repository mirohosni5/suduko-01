package View;

import Controller.ControllerFacade;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class SudokuGUI extends JFrame {
    
    private final controllable controller;
    private JTextField[][] cells;
    private int[][] currentBoard;
    private int[][] originalBoard;
    private JButton verifyButton;
    private JButton solveButton;
    private JButton undoButton;
    private JLabel statusLabel;
    private char currentDifficulty;
    ControllerFacade facade = new ControllerFacade();
    public SudokuGUI() {
        this.controller = new ViewControllerAdapter(facade);
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initializeGame();
    }
    private void initializeGame() {
        try {
            boolean[] catalog = controller.getCatalog();
            boolean hasUnfinished = catalog[0];
            boolean hasAllDifficulties = catalog[1];
            if (hasUnfinished) {
                int response = JOptionPane.showConfirmDialog(this,"continue your previous game?","Continue Game",JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    currentBoard = controller.getGame('I');
                    originalBoard = copyBoard(currentBoard);
                    currentDifficulty = 'I';
                    createGameUI();
                    return;
                }
            }
            if (!hasAllDifficulties) {
                generateGamesFromSource();
            }
            selectDifficulty(); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error initializing game: " + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void generateGamesFromSource() {
        JOptionPane.showMessageDialog(this,"Select solved Sudoku file to generate games.","Generate Games",JOptionPane.INFORMATION_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Solved Sudoku File");
        fileChooser.setCurrentDirectory(new File("."));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                controller.driveGames(selectedFile.getAbsolutePath());
                JOptionPane.showMessageDialog(this,"Games generated successfully","Success",JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,"Error generating games","Error",JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }
    private void selectDifficulty() {
        String[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(this,"Select difficulty level:","Difficulty Selection",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        if (choice >= 0) {
            try {
                char level = "EMH".charAt(choice);
                currentBoard = controller.getGame(level);
                originalBoard = copyBoard(currentBoard);
                currentDifficulty = level;
                createGameUI();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,"Error loading game: " + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                selectDifficulty();
            }
        } else {
            selectDifficulty();
        }
    }
    private void createGameUI() {
        getContentPane().removeAll();
        JPanel mainPanel = new JPanel(new BorderLayout(50, 50));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        JPanel boardPanel = createBoardPanel();
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        statusLabel = new JLabel("Fill the empty cells", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(statusLabel, BorderLayout.NORTH);
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        updateSolveButton();
    }
    private JPanel createBoardPanel() {
        JPanel boardPanel = new JPanel(new GridLayout(9, 9, 2, 2));
        boardPanel.setBackground(Color.BLACK);
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));   
        cells = new JTextField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new JTextField();
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                cells[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                if ((i / 3 + j / 3) % 2 == 0) {
                    cells[i][j].setBackground(new Color(230, 230, 250));
                } else {
                    cells[i][j].setBackground(Color.WHITE);
                }
                final int row = i;
                final int col = j;
                if (originalBoard[i][j] != 0) {
                    cells[i][j].setText(String.valueOf(originalBoard[i][j]));
                    cells[i][j].setEditable(false);
                    cells[i][j].setForeground(Color.BLACK);
                    cells[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                } else {
                    if (currentBoard[i][j] != 0) {
                        cells[i][j].setText(String.valueOf(currentBoard[i][j]));
                    }
                    cells[i][j].setForeground(Color.BLUE);
                    
                    cells[i][j].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            handleCellChange(row, col);
                        }
                    });
                }
                
                boardPanel.add(cells[i][j]);
            }
        }
        return boardPanel;
    }
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        verifyButton = new JButton("Verify");
        verifyButton.setFont(new Font("Arial", Font.BOLD, 14));
        verifyButton.addActionListener(e -> verifyGame());
        
        solveButton = new JButton("Solve");
        solveButton.setFont(new Font("Arial", Font.BOLD, 14));
        solveButton.addActionListener(e -> solveGame());
        
        undoButton = new JButton("Undo");
        undoButton.setFont(new Font("Arial", Font.BOLD, 14));
        undoButton.addActionListener(e -> undoLastMove());
        
        controlPanel.add(verifyButton);
        controlPanel.add(solveButton);
        controlPanel.add(undoButton);
        
        return controlPanel;
    }
    
    private void handleCellChange(int row, int col) {
        String text = cells[row][col].getText().trim();
        
        if (text.isEmpty()) {
            logAction(row, col, currentBoard[row][col], 0);
            currentBoard[row][col] = 0;
        } else {
            try {
                int value = Integer.parseInt(text);
                if (value >= 1 && value <= 9) {
                    logAction(row, col, currentBoard[row][col], value);
                    currentBoard[row][col] = value;
                } else {
                    cells[row][col].setText("");
                    currentBoard[row][col] = 0;
                }
            } catch (NumberFormatException e) {
                cells[row][col].setText("");
                currentBoard[row][col] = 0;
            }
        }
        updateSolveButton();
    }
    
    private void logAction(int row, int col, int oldValue, int newValue) {
        try {
            UserAction action = new UserAction(row, col, oldValue);
            controller.logUserAction(action);
        } catch (Exception e) {
            System.err.println("Error logging action: " + e.getMessage());
        }
    }
    
    private void verifyGame() {
        try {
            boolean[][] validation = controller.verifyGame(currentBoard);
            boolean allValid = true;
            boolean hasEmpty = false;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (currentBoard[i][j] == 0) {
                        hasEmpty = true;
                    }
                    
                    if (!validation[i][j] && currentBoard[i][j] != 0) {
                        allValid = false;
                        cells[i][j].setBackground(new Color(255, 200, 200));
                    } else {
                        if ((i / 3 + j / 3) % 2 == 0) {
                            cells[i][j].setBackground(new Color(230, 230, 250));
                        } else {
                            cells[i][j].setBackground(Color.WHITE);
                        }
                    }
                }
            }
            
            if (allValid && !hasEmpty) {
                statusLabel.setText("Valid Sudoku Solution");
                statusLabel.setForeground(new Color(0, 150, 0));
                JOptionPane.showMessageDialog(this,"Valid Solution","Success",JOptionPane.INFORMATION_MESSAGE);
            } else if (allValid) {
                statusLabel.setText("Valid for now, Keep going");
                statusLabel.setForeground(new Color(0, 100, 200));
            } else {
                statusLabel.setText("Invalid cells highlighted in red");
                statusLabel.setForeground(Color.RED);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error verifying game: " + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void solveGame() {
        try {
            int[][] solution = controller.solveGame(currentBoard);
            
            for (int i = 0; i < solution.length; i++) {
                int row = solution[i][0];
                int col = solution[i][1];
                int value = solution[i][2];
                
                cells[row][col].setText(String.valueOf(value));
                cells[row][col].setForeground(new Color(0, 150, 0));
                currentBoard[row][col] = value;
            }
            
            statusLabel.setText("Puzzle solved!");
            statusLabel.setForeground(new Color(0, 150, 0));
            solveButton.setEnabled(false);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error solving game: " + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void undoLastMove() {
        try {
            facade.undoLastAction(); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error undoing: " + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void updateSolveButton() {
        int emptyCount = countEmptyCells();
        solveButton.setEnabled(emptyCount == 5);
        if (emptyCount == 5) {
            statusLabel.setText("5 empty cells - Solve button enabled!");
            statusLabel.setForeground(new Color(0, 100, 200));
        }
    }
    private int countEmptyCells() {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (currentBoard[i][j] == 0) count++;
                }}
        return count;
    }
    private int[][] copyBoard(int[][] board) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, 9);
        }
        return copy;
    }
          public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SudokuGUI();
        });
    }  
}