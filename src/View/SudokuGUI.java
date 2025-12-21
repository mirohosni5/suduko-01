package View;

import controller.ControllerFacade;
import SudokuSolutionVerifier.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Random;

public class SudokuGUI extends JFrame {
    private JTextField[][] cells = new JTextField[9][9];
    private final controllable controller;
    public SudokuGUI(controllable controller) {
        this.controller = controller;
        initUI();
        startupFlow();
    }
     private void initUI() {
        setTitle("Sudoku");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel grid = new JPanel(new GridLayout(9, 9));
        Font f = new Font("Arial", Font.BOLD, 18);
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                JTextField tf = new JTextField();
                tf.setFont(f);
                tf.setHorizontalAlignment(JTextField.CENTER);
                cells[i][j] = tf;
                grid.add(tf);
            }
        JButton verify = new JButton("Verify");
        JButton solve = new JButton("Solve");
        verify.addActionListener(e -> verify());
        solve.addActionListener(e -> solve());
        JPanel buttons = new JPanel();
        buttons.add(verify);
        buttons.add(solve);
        add(grid, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }
       private void startupFlow() {
        try {
            Catalog cat = controller.getCatalog();
            if (!cat.allModesExist()) {
                JFileChooser chooser = new JFileChooser();
                JOptionPane.showMessageDialog(this,"Select a SOLVED Sudoku file");
                if (chooser.showOpenDialog(this)!= JFileChooser.APPROVE_OPTION)
                    System.exit(0);
                File file = chooser.getSelectedFile();
                Game sourceGame =ControllerFacade.loadGameFromFile(file.getAbsolutePath());
                controller.driveGames(sourceGame);
            }
            DifficultyEnum level = askDifficulty();
            Game game = controller.getGame(level);
            fillBoard(game.getBoard());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            System.exit(0);
        }
    }
    private DifficultyEnum askDifficulty() {
        Object[] opts = {"Easy", "Medium", "Hard"};
        int c = JOptionPane.showOptionDialog(this,"Choose difficulty","Difficulty",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null, opts, opts[0]);
        return switch (c) {
            case 1 -> DifficultyEnum.MEDIUM;
            case 2 -> DifficultyEnum.HARD;
            default -> DifficultyEnum.EASY;
        };
    }
     private void fillBoard(int[][] b) {
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                if (b[i][j] == 0) {
                    cells[i][j].setText("");
                    cells[i][j].setEditable(true);
                } else {
                    cells[i][j].setText("" + b[i][j]);
                    cells[i][j].setEditable(false);}
            }   }
    }
    private int[][] readBoard() {
        int[][] b = new int[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                String t = cells[i][j].getText();
                b[i][j] = t.isEmpty() ? 0 : Integer.parseInt(t);
            }
        return b;
    }
    private void verify() {
        String r = controller.verifyGame(
                new Game(readBoard()));
        JOptionPane.showMessageDialog(this, r);
    }

    private void solve() {
        try {
            int[] sol = controller.solveGame(
                    new Game(readBoard()));
            for (int i = 0; i < sol.length; i += 3)
                cells[sol[i]][sol[i + 1]]
                        .setText("" + sol[i + 2]);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->new SudokuGUI(new ControllerFacade()).setVisible(true));
    }
}

   