/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;


import controller.DifficultyEnum;
import controller.Viewable;
import model.Catalog;
import model.Game;

/**
 *
 * @author M
 */
public class ViewControllerAdapter implements controllable {
    private final Viewable controller;
    
    public ViewControllerAdapter(Viewable controller) {
        this.controller = controller;
    }
     @Override
    public boolean[] getCatalog() {
        Catalog catalog = controller.getCatalog();
        return new boolean[] {
            catalog.hasCurrentGame(),
            catalog.allModesExist()
        };
    }
    
    @Override
    public int[][] getGame(char level) throws Exception {
        DifficultyEnum difficulty;
        
        switch (level) {
            case 'E':
            case 'e':
                difficulty = DifficultyEnum.EASY;
                break;
            case 'M':
            case 'm':
                difficulty = DifficultyEnum.MEDIUM;
                break;
            case 'H':
            case 'h':
                difficulty = DifficultyEnum.HARD;
                break;
            case 'I':
            case 'i':
                difficulty = DifficultyEnum.INCOMPLETE;
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty level: " + level);
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
        Game gameObj = new Game(game);
        String result = controller.verifyGame(gameObj);
        
        boolean[][] validationResult = new boolean[9][9];
        
        // Initialize all cells as valid
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                validationResult[i][j] = true;
            }
        }
        
        // If invalid, parse the invalid cell positions
        if (result.startsWith("INVALID")) {
            String[] parts = result.split(" ");
            for (int i = 1; i < parts.length; i++) {
                String[] coords = parts[i].split(",");
                int row = Integer.parseInt(coords[0]);
                int col = Integer.parseInt(coords[1]);
                validationResult[row][col] = false;
            }
        }
        
        return validationResult;
    }
    
    @Override
    public int[][] solveGame(int[][] game) throws Exception {
        Game gameObj = new Game(game);
        int[] solution = controller.solveGame(gameObj);
        
        // Convert flat array to 2D array
        // Format: [row0, col0, val0, row1, col1, val1, ...]
        int[][] result = new int[5][3];
        for (int i = 0; i < 5; i++) {
            result[i][0] = solution[i * 3];     // row
            result[i][1] = solution[i * 3 + 1]; // col
            result[i][2] = solution[i * 3 + 2]; // value
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

