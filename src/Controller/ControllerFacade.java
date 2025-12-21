package Controller;

import SudokuSolutionVerifier.GameState;
import SudokuSolutionVerifier.ValidationResult;
import View.UserAction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.Catalog;
import model.Game;
import model.SudokuSolver;
import model.SudokuVerifier;
import utils.InvalidGame;
import utils.RandomPairs;
import utils.StorageManager;

public class ControllerFacade implements Viewable {

    private final StorageManager storage = new StorageManager();
    private final SudokuSolver solver = new SudokuSolver();
    private final SudokuVerifier s = new SudokuVerifier();

    private int[][] currentBoard;
    private DifficultyEnum currentLevel;

    @Override
    public Catalog getCatalog() {
        boolean hasCurrent = storage.hasCurrentGame();
        boolean hasAll = storage.hasAllDifficulties();
        return new Catalog(hasCurrent, hasAll);
    }

    @Override
    public Game getGame(DifficultyEnum level) throws Exception {
        if (level == DifficultyEnum.INCOMPLETE) {
            currentBoard = storage.loadCurrentGame();
        } else {
            currentBoard = storage.loadGame(level);
            storage.saveCurrentGame(currentBoard);
            currentLevel = level;
        }
        return new Game(currentBoard);
    }

    @Override
    public void driveGames(Game sourceGame) throws Exception {
        int[][] source = sourceGame.getBoard();

        if (s.verify(source) != GameState.VALID)
            throw new InvalidGame("Source Sudoku is not VALID");

        RandomPairs rp = new RandomPairs();
        int[][] easy = copy(source);
        int[][] medium = copy(source);
        int[][] hard = copy(source);

        for (int[] p : rp.generateDistinctPairs(10))
            easy[p[0]][p[1]] = 0;

        for (int[] p : rp.generateDistinctPairs(20))
            medium[p[0]][p[1]] = 0;

        for (int[] p : rp.generateDistinctPairs(25))
            hard[p[0]][p[1]] = 0;

        storage.saveGame(DifficultyEnum.EASY, easy);
        storage.saveGame(DifficultyEnum.MEDIUM, medium);
        storage.saveGame(DifficultyEnum.HARD, hard);
    }

    @Override
    public String verifyGame(Game game) {
        GameState g = s.verify(game.getBoard());
        return g.name();
    }

    public boolean[][] verifyGame(int[][] board) {
        s.verify(board);
        return s.getValidationMatrix(board);
    }


    @Override
    public int[] solveGame(Game game) throws Exception {
        return solver.solve(game.getBoard());
    }

    @Override
    public void logUserAction(String action) throws Exception {
        UserAction userAction = UserAction.fromString(action);
        storage.appendLog(userAction);

        if (currentBoard != null) {
            String[] parts = action.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            int newValue = Integer.parseInt(parts[2]);
            currentBoard[row][col] = newValue;
            storage.saveCurrentGame(currentBoard);
        }
    }

    public UserAction undoLastAction() throws Exception {
        UserAction lastAction = storage.getLastLog();
        if (lastAction != null && currentBoard != null) {
            currentBoard[lastAction.row][lastAction.col] = lastAction.oldValue;
            storage.saveCurrentGame(currentBoard);
            storage.removeLastLog();
        }
        return lastAction;
    }
    public ValidationResult getDetailedValidation(int[][] board) {
    s.verify(board);
    return s.getValidationResult();
}
    
    private int[][] loadBoardFromFile(String path) throws IOException {
        int[][] b = new int[9][9];
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            for (int i = 0; i < 9; i++) {
                String[] line = br.readLine().trim().split("\\s+");
                for (int j = 0; j < 9; j++)
                    b[i][j] = Integer.parseInt(line[j]);
            }
        }
        return b;
    }

    private int[][] copy(int[][] src) {
        int[][] c = new int[9][9];
        for (int i = 0; i < 9; i++)
            c[i] = src[i].clone();
        return c;
    }
}