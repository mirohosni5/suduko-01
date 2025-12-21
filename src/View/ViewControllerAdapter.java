/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import Controller.ControllerFacade;
import Controller.DifficultyEnum;
import Controller.Viewable;
import SudokuSolutionVerifier.ValidationResult;
import SudokuSolutionVerifier.DuplicateValue;
import java.util.HashSet;
import java.util.Set;
import model.Catalog;
import model.Game;

/**
 *
 * @author M
 */
public class ViewControllerAdapter implements controllable {
    private final Viewable controller;
    private final ControllerFacade facade;
    
    public ViewControllerAdapter(Viewable controller) {
        this.controller = controller;
        this.facade = (ControllerFacade) controller;
    }
     @Override
    public boolean[] getCatalog() {
        Catalog catalog = controller.getCatalog();
        return new boolean[] {catalog.hasCurrentGame(),catalog.allModesExist()};
    }
    @Override
    public int[][] getGame(char level) throws Exception {
        DifficultyEnum difficulty;
        
        switch (level) {
            case 'E', 'e' -> difficulty = DifficultyEnum.EASY;
            case 'M', 'm' -> difficulty = DifficultyEnum.MEDIUM;
            case 'H', 'h' -> difficulty = DifficultyEnum.HARD;
            case 'I', 'i' -> difficulty = DifficultyEnum.INCOMPLETE;
            default -> throw new IllegalArgumentException("Invalid difficulty level: " + level);
        }
        Game game = controller.getGame(difficulty);
        return game.getBoard();
    }
    @Override
    public void driveGames(String sourcePath) throws Exception {
        int[][] board = loadBoardFromFile(sourcePath);
        Game sourceGame = new Game(board);
        controller.driveGames(sourceGame);
    }
    @Override
    public boolean[][] verifyGame(int[][] game) {
         boolean[][] validationResult = new boolean[9][9];
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                validationResult[i][j] = true;
            }
        }
        
        ValidationResult result = facade.getDetailedValidation(game);
        Set<String> invalidCells = new HashSet<>();
        
        for (DuplicateValue dup : result.getRowDups()) {
            int row = dup.getIndex() - 1;
            for (int colPos : dup.getPositions()) {
                int col = colPos;
                invalidCells.add(row + "," + col);
            }
        }
        
        for (DuplicateValue dup : result.getColDups()) {
            int col = dup.getIndex() - 1;
            for (int rowPos : dup.getPositions()) {
                int row = rowPos;
                invalidCells.add(row + "," + col);
            }
        }
        
        for (DuplicateValue dup : result.getBoxDups()) {
            int boxIndex = dup.getIndex() - 1;
            int boxRow = boxIndex / 3;
            int boxCol = boxIndex % 3;
            
            for (int linearPos : dup.getPositions()) {
                int localRow = linearPos / 3;
                int localCol = linearPos % 3;
                int row = boxRow * 3 + localRow;
                int col = boxCol * 3 + localCol;
                invalidCells.add(row + "," + col);
            }
        }
        
        for (String cell : invalidCells) {
            String[] parts = cell.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            validationResult[row][col] = false;
        }
        
        return validationResult;
    }
    @Override
    public int[][] solveGame(int[][] game) throws Exception {
        Game g = new Game(game);
        int[] solution = controller.solveGame(g);
        int[][] result = new int[5][3];
        for (int i = 0; i < 5; i++) {
            result[i][0] = solution[i * 3];    
            result[i][1] = solution[i * 3 + 1]; 
            result[i][2] = solution[i * 3 + 2]; 
        }
        return result;
    }
    @Override
    public void logUserAction(UserAction userAction) throws Exception {
        String actionString = userAction.toString();
        controller.logUserAction(actionString);
    }
    
    private int[][] loadBoardFromFile(String path) throws Exception {
        return utils.StorageManager.loadBoard(path);
    }
}

