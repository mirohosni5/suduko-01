/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuSolutionVerifier;

import java.io.File;
import java.io.FileNotFoundException;
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
    
    
}
