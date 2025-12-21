package SudokuSolutionVerifier;

import java.util.*;

public class SudokuVerifier {
    public VerificationResult verify(Game game) {

        int[][] board = game.getBoard();

        private boolean checkRows(int[][] board) {
            for (int i = 0; i < 9; i++) {
                boolean[] seen = new boolean[10];
                for (int j = 0; j < 9; j++) {
                    int value = board[i][j];
                    if (seen[value]) return false;
                    seen[value] = true;
                }
            }
            return true;
        }

        private boolean checkColumns(int[][] board) {
            for (int j = 0; j < 9; j++) {
                boolean[] seen = new boolean[10];
                for (int i = 0; i < 9; i++) {
                    int value = board[i][j];
                    if (seen[value]) return false;
                    seen[value] = true;
                }
            }
            return true;
        }

        private boolean checkBoxes(int[][] board) {
            for (int row = 0; row < 9; row += 3) {
                for (int col = 0; col < 9; col += 3) {
                    boolean[] seen = new boolean[10];
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            int value = board[row + i][col + j];
                            if (seen[value]) return false;
                            seen[value] = true;
                        }
                    }
                }
            }
            return true;
        }

    }
}
