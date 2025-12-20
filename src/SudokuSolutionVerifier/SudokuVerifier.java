package SudokuSolutionVerifier;

import java.util.*;

public class SudokuVerifier {
    public VerificationResult verify(int[][] board) {
        Set<Cell> invalidCells = new HashSet<>();
        boolean hasZero = false;

        for (int i = 0; i < 9; i++) {
            boolean[] used = new boolean[10];
            Cell[] firstPos = new Cell[10];

            for (int j = 0; j < 9; j++) {
                int val = board[i][j];

                if (val == 0) {
                    hasZero = true;
                    continue;
                }

                if (used[val]) {
                    invalidCells.add(firstPos[val]);
                    invalidCells.add(new Cell(i, j));
                } else {
                    used[val] = true;
                    firstPos[val] = new Cell(i, j);
                }
            }
        }


    }

}
