package model;

import SudokuSolutionVerifier.DuplicateValue;
import SudokuSolutionVerifier.GameState;
import SudokuSolutionVerifier.ValidationResult;

import java.util.*;

public class SudokuVerifier {

    private final List<DuplicateValue> rowDups = new ArrayList<>();
    private final List<DuplicateValue> colDups = new ArrayList<>();
    private final List<DuplicateValue> boxDups = new ArrayList<>();

    public GameState verify(int[][] board) {

        rowDups.clear();
        colDups.clear();
        boxDups.clear();

        boolean incomplete = false;

        //chack rows
        for (int i = 0; i < 9; i++) {
            boolean[] seen = new boolean[10];
            List<Integer>[] positions = new ArrayList[10];
            for (int k = 1; k <= 9; k++) positions[k] = new ArrayList<>();

            for (int j = 0; j < 9; j++) {
                int v = board[i][j];

                if (v == 0) {
                    incomplete = true;
                    continue;
                }

                positions[v].add(j);

                if (!seen[v]) {
                    seen[v] = true;
                }
            }

            for (int v = 1; v <= 9; v++) {
                if (positions[v].size() > 1) {
                    rowDups.add(
                            new DuplicateValue("ROW", i + 1, v, positions[v])
                    );
                }
            }
        }

        //check columns
        for (int j = 0; j < 9; j++) {
            boolean[] seen = new boolean[10];
            List<Integer>[] positions = new ArrayList[10];
            for (int k = 1; k <= 9; k++) positions[k] = new ArrayList<>();

            for (int i = 0; i < 9; i++) {
                int v = board[i][j];

                if (v == 0) {
                    incomplete = true;
                    continue;
                }

                positions[v].add(i);

                if (!seen[v]) {
                    seen[v] = true;
                }
            }

            for (int v = 1; v <= 9; v++) {
                if (positions[v].size() > 1) {
                    colDups.add(
                            new DuplicateValue("COLUMN", j + 1, v, positions[v])
                    );
                }
            }
        }

        //check boxes
        for (int boxRow = 0; boxRow < 9; boxRow += 3) {
            for (int boxCol = 0; boxCol < 9; boxCol += 3) {

                List<Integer>[] positions = new ArrayList[10];
                for (int k = 1; k <= 9; k++) positions[k] = new ArrayList<>();

                int boxIndex = (boxRow / 3) * 3 + (boxCol / 3) + 1;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {

                        int v = board[boxRow + i][boxCol + j];
                        int linearPos = i * 3 + j;

                        if (v == 0) {
                            incomplete = true;
                            continue;
                        }

                        positions[v].add(linearPos);
                    }
                }

                for (int v = 1; v <= 9; v++) {
                    if (positions[v].size() > 1) {
                        boxDups.add(
                                new DuplicateValue("BOX", boxIndex, v, positions[v])
                        );
                    }
                }
            }
        }

        if (!rowDups.isEmpty() || !colDups.isEmpty() || !boxDups.isEmpty())
            return GameState.INVALID;

        if (incomplete)
            return GameState.INCOMPLETE;

        return GameState.VALID;
    }

    public boolean[][] getValidationMatrix(int[][] board) {
        boolean[][] valid = new boolean[9][9];

        for (int i = 0; i < 9; i++)
            Arrays.fill(valid[i], true);

        // check row
        for (DuplicateValue dv : rowDups) {
            int row = dv.getIndex() - 1;
            for (int col : dv.getPositions()) {
                valid[row][col] = false;
            }
        }

        // check column
        for (DuplicateValue dv : colDups) {
            int col = dv.getIndex() - 1;
            for (int row : dv.getPositions()) {
                valid[row][col] = false;
            }
        }

        // check box
        for (DuplicateValue dv : boxDups) {
            int boxIndex = dv.getIndex() - 1;
            int boxStartRow = (boxIndex / 3) * 3;
            int boxStartCol = (boxIndex % 3) * 3;
            for (int pos : dv.getPositions()) {
                int r = boxStartRow + (pos / 3);
                int c = boxStartCol + (pos % 3);
                valid[r][c] = false;
            }
        }

        // empty cells
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (board[i][j] == 0) valid[i][j] = false;

        return valid;
    }

    public ValidationResult getValidationResult() {
        return new ValidationResult(rowDups, colDups, boxDups);
    }
}
