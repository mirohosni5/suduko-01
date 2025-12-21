package controller;

import utils.ActionLogger;
import SudokuSolutionVerifier.GameDriver;
import utils.InvalidGame;
import utils.NotFoundException;
import SudokuSolutionVerifier.SequentialMode;
import utils.StorageManager;
import SudokuSolutionVerifier.UndoManager;
import View.UserAction;
import View.controllable;
import model.SudokuVerifier;
import model.SudokuSolver;
import model.Game;
import model.Catalog;
import java.io.IOException;

public class ControllerFacade implements Viewable{

    private int[][] board;
    private UndoManager undoManager;
    private int[][] initialBoard; 
    private DifficultyEnum currentDifficulty;
    private final StorageManager storage = new StorageManager();
    private final SequentialMode verifier = new SequentialMode();
    private final SudokuVerifier detailedVerifier = new SudokuVerifier();
    private final SudokuSolver solver = new SudokuSolver();
    private final GameDriver driver = new GameDriver();

    public ControllerFacade(int[][] board) {
        this.board = board;
         this.initialBoard = GameDriver.copyBoard(board);
        this.undoManager = new UndoManager();
    }
     public Catalog getCatalog() {
        boolean hasCurrent = storage.hasCurrentGame();
        boolean hasAll = storage.hasAllDifficulties();
        return new Catalog(hasCurrent, hasAll);
    }
      public Game getGame(DifficultyEnum level) throws NotFoundException, IOException {
        try {
            if (level == DifficultyEnum.INCOMPLETE) {
                board = storage.loadCurrentGame();
            } else {
                board = storage.loadGame(level);
                storage.saveCurrentGame(board);
                currentDifficulty = level;
            }
            initialBoard = GameDriver.copyBoard(board);
            undoManager = new UndoManager();
            return new Game(board);
        } catch (IOException e) {
            throw new NotFoundException("Could not load game: " + e.getMessage());
        }
    }
        public void driveGames(int[][] sourceBoard) throws InvalidGame {
        try {
            driver.generateAllVerified(sourceBoard);
        } catch (IOException e) {
            throw new InvalidGame("Error saving games: " + e.getMessage());
        }
    }
    public void changeCell(int row, int col, int value) {

        int oldValue = board[row][col];

        // we are saving here for the undo ascton
        undoManager.addAction(row, col, oldValue);

        // we are writing here for the log file..
        ActionLogger.log(row, col, oldValue, value);

        // updating the board
        board[row][col] = value;
    }

    // then this the undo action.
    public void undo() {
        UserAction action = undoManager.undo();
        if (action != null) {
            board[action.row][action.col] = action.oldValue;
        }
    }

    public int[][] getBoard() {
        return board;
    }
    @Override
    public void driveGames(Game sourceGame) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String verifyGame(Game game) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int[] solveGame(Game game) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void logUserAction(String action) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
