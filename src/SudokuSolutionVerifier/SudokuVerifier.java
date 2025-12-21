package SudokuSolutionVerifier;

import java.util.*;

public class SudokuVerifier {

    private List<DuplicateValue> rowDups;
    private List<DuplicateValue> colDups;
    private List<DuplicateValue> boxDups;

    public SudokuVerifier() {
        rowDups = new ArrayList<>();
        colDups = new ArrayList<>();
        boxDups = new ArrayList<>();
    }

    public GameState verify(int[][] board) {
        rowDups.clear();
        colDups.clear();
        boxDups.clear();

        boolean incomplete = false;

        //Check Rows
        for (int i = 0; i < 9; i++) {
            boolean[] seen = new boolean[10];
            List<Integer>[] positions = new ArrayList[10];
            for (int v = 0; v <= 9; v++) positions[v] = new ArrayList<>();

            for (int j = 0; j < 9; j++) {
                int v = board[i][j];
                if (v == 0) {
                    incomplete = true;
                    continue;
                }

                if (seen[v]) {
                    List<Integer> pos = new ArrayList<>(positions[v]);
                    pos.add(j);
                    rowDups.add(new DuplicateValue("ROW", i, v, pos));
                } else {
                    seen[v] = true;
                    positions[v].add(j);
                }
            }
        }

        //Check Columns
        for (int j = 0; j < 9; j++) {
            boolean[] seen = new boolean[10];
            List<Integer>[] positions = new ArrayList[10];
            for (int v = 0; v <= 9; v++) positions[v] = new ArrayList<>();

            for (int i = 0; i < 9; i++) {
                int v = board[i][j];
                if (v == 0) {
                    incomplete = true;
                    continue;
                }

                if (seen[v]) {
                    List<Integer> pos = new ArrayList<>(positions[v]);
                    pos.add(i);
                    colDups.add(new DuplicateValue("COLUMN", j, v, pos));
                } else {
                    seen[v] = true;
                    positions[v].add(i);
                }
            }
        }

        //Check Boxes
        for (int boxRow = 0; boxRow < 9; boxRow += 3) {
            for (int boxCol = 0; boxCol < 9; boxCol += 3) {

                boolean[] seen = new boolean[10];
                List<Integer>[] positions = new ArrayList[10];
                for (int v = 0; v <= 9; v++) positions[v] = new ArrayList<>();
                int boxIndex = (boxRow / 3) * 3 + (boxCol / 3);

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int v = board[boxRow + i][boxCol + j];
                        int linearPos = i * 3 + j;

                        if (v == 0) {
                            incomplete = true;
                            continue;
                        }

                        if (seen[v]) {
                            List<Integer> pos = new ArrayList<>(positions[v]);
                            pos.add(linearPos);
                            boxDups.add(new DuplicateValue("BOX", boxIndex, v, pos));
                        } else {
                            seen[v] = true;
                            positions[v].add(linearPos);
                        }
                    }
                }
            }
        }


        if (!rowDups.isEmpty() || !colDups.isEmpty() || !boxDups.isEmpty()) return GameState.INVALID;
        if (incomplete) return GameState.INCOMPLETE;
        return GameState.VALID;
    }

    public ValidationResult getValidationResult() {
        return new ValidationResult(rowDups, colDups, boxDups);
    }
}
