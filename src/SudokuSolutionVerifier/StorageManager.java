/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuSolutionVerifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author M
 */
public class StorageManager {
    public static void saveBoard (String path, int[][] board) throws FileNotFoundException{
        File f = new File(path);
        f.getParentFile().mkdirs();
        try(PrintWriter p = new PrintWriter(f)){
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    p.print(board[i][j] + " ");
                }
                p.println();
            }
        }
    }
    public static int[][] loadBoard(String path) throws FileNotFoundException, IOException  {
        int[][] board = new int[9][9];

        try (BufferedReader b = new BufferedReader(new FileReader(path))) {
            for (int i = 0; i < 9; i++) {
                String[] line = b.readLine().split(" ");
                for (int j = 0; j < 9; j++) {
                    board[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
        return board;
    }
    
}
