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




    }
}
