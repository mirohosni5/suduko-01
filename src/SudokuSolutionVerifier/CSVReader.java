/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuSolutionVerifier;

import java.nio.file.*;
import java.util.*;

public class CSVReader {

    public static int[][] load(String path) throws Exception {
        int[][] board = new int[9][9];
        String data = new String(Files.readAllBytes(Paths.get(path)));
        String[] tokens = data.split("[,\\n\\r]+");
        int n = Math.min(81, tokens.length);
        for (int i = 0; i < n; i++) {
            try {
                board[i / 9][i % 9] = Integer.parseInt(tokens[i].trim());
            } catch (Exception e) {
                board[i / 9][i % 9] = 0;
            }
        }
        return board;
    }
}

