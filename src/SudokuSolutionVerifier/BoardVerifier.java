package SudokuSolutionVerifier;
//engyz

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BoardVerifier {
     public static boolean verifyWithCombination(int[][] board, List<int[]> emptyCells, int[] combination) {
        
        Map<String, Integer> tempState = new HashMap<>();
        for (int i = 0; i < emptyCells.size(); i++) {
            int[] cell = emptyCells.get(i);
            tempState.put(cell[0] + "," + cell[1], combination[i]);
        }
        return isValidBoard(board, tempState);
    }
    private static boolean isValidBoard(int[][] board, Map<String, Integer> tempState) {
        for (int row = 0; row < 9; row++) {
            if (!isValidUnit(board, tempState, row, 0, 0, 1)) {
                return false;
            }
        }
        for (int col = 0; col < 9; col++) {
            if (!isValidUnit(board, tempState, 0, col, 1, 0)) {
                return false;
            }
        }
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                if (!isValid3x3Box(board, tempState, boxRow * 3, boxCol * 3)) 
                    return false;
            }
        }
        return true;
    }
    private static boolean isValidUnit(int[][] board, Map<String, Integer> tempState,int startRow, int startCol, int rowInc, int colInc) {
        Set<Integer> seen = new HashSet<>();
        int row = startRow;
        int col = startCol;
        for (int i = 0; i < 9; i++) {
            int value = getValue(board, tempState, row, col);
            if (value != 0) {
                if (seen.contains(value)) return false;
                seen.add(value);
            }
            row += rowInc;
            col += colInc;
        }
        return true;
    }
    private static boolean isValid3x3Box(int[][] board, Map<String, Integer> tempState,int startRow, int startCol) {
        Set<Integer> seen = new HashSet<>();
        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                int value = getValue(board, tempState, row, col);
                if (value != 0) {
                    if (seen.contains(value)) return false;
                    seen.add(value);
                }
            }
        }
        return true;
    }
    private static int getValue(int[][] board, Map<String, Integer> tempState,int row, int col) {
        String key = row + "," + col;
        if (tempState.containsKey(key)) return tempState.get(key);
        return board[row][col];
    }
}
