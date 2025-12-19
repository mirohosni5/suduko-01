/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuSolutionVerifier;

import java.io.FileNotFoundException;

/**
 *
 * @author M
 */
public class GameDriver {
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
     public static int[][] copyBoard(int[][] b) {
        int[][] c = new int[9][9];
        for (int i = 0; i < 9; i++)
            c[i] = b[i].clone();
        return c;
    }
    
}
