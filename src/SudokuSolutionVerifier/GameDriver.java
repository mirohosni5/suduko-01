/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuSolutionVerifier;

import utils.StorageManager;
import utils.RandomPairs;
import utils.InvalidGame;
import Controller.DifficultyEnum;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author M
 */
public class GameDriver {
    private final StorageManager storage = new StorageManager();
    private final SequentialMode verifier = new SequentialMode();
    private final RandomPairs randomPairs = new RandomPairs();
    private static final String ROOT= "games/";
    public static void generateAll(int[][] board) throws FileNotFoundException{
        generateLevel(board, 10, "easy");
        generateLevel(board, 20, "medium");
        generateLevel(board, 25, "hard");
    }
    public static void generateLevel(int[][] source, int numOfCells, String level) throws FileNotFoundException{
        int[][] board = copyBoard(source);
         RandomPairs r = new RandomPairs();
          for (int[] p : r.generateDistinctPairs(numOfCells)) {
            board[p[0]][p[1]] = 0;
        }
          StorageManager.saveBoard(ROOT + level + "/game.txt",board);
    }
     public void generateAllVerified(int[][] sourceBoard)throws InvalidGame, IOException {
        ValidationResult result = verifier.verify(sourceBoard);
        
        if (!result.isValid()) {
            throw new InvalidGame("Source solution has conflicts");
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sourceBoard[i][j] == 0) 
                    throw new InvalidGame("Source solution is incomplete");
            }
        }
        int[][] easyBoard = copyBoard(sourceBoard);
        int[][] mediumBoard = copyBoard(sourceBoard);
        int[][] hardBoard = copyBoard(sourceBoard);
        List<int[]> easyPairs = randomPairs.generateDistinctPairs(10);
        List<int[]> mediumPairs = randomPairs.generateDistinctPairs(20);
        List<int[]> hardPairs = randomPairs.generateDistinctPairs(25);
        for (int[] pair : easyPairs) {
            easyBoard[pair[0]][pair[1]] = 0;
        }
        for (int[] pair : mediumPairs) {
            mediumBoard[pair[0]][pair[1]] = 0;
        }
        for (int[] pair : hardPairs) {
            hardBoard[pair[0]][pair[1]] = 0;
        }
        storage.saveGame(DifficultyEnum.EASY, easyBoard);
        storage.saveGame(DifficultyEnum.MEDIUM, mediumBoard);
        storage.saveGame(DifficultyEnum.HARD, hardBoard);
    }
     public static int[][] copyBoard(int[][] b) {
        int[][] c = new int[9][9];
        for (int i = 0; i < 9; i++)
            c[i] = b[i].clone();
        return c;
    }
    
}
