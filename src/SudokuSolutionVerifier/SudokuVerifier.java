package SudokuSolutionVerifier;

import java.util.*;

public class SudokuVerifier {
    public VerificationResult verify(int[][] board) {
        Set<Cell> invalidCells = new HashSet<>();
        boolean hasZero = false;

        for (int i = 0; i < 9; i++) {   //check rows
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

        for (int j = 0; j < 9; j++) {     //check columns
            boolean[] used = new boolean[10];
            Cell[] firstPos = new Cell[10];

            for (int i = 0; i < 9; i++) {
                int val = board[i][j];

                if (val == 0) continue;

                if (used[val]) {
                    invalidCells.add(firstPos[val]);
                    invalidCells.add(new Cell(i, j));
                } else {
                    used[val] = true;
                    firstPos[val] = new Cell(i, j);
                }
            }
        }

        for (int br = 0; br < 3; br++) {    //check boxes
            for (int bc = 0; bc < 3; bc++) {

                boolean[] used = new boolean[10];
                Cell[] firstPos = new Cell[10];

                for (int i = br * 3; i < br * 3 + 3; i++) {
                    for (int j = bc * 3; j < bc * 3 + 3; j++) {

                        int val = board[i][j];
                        if (val == 0) continue;

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





    }

}
