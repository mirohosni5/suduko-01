package SudokuSolutionVerifier;
import java.util.ArrayList;
import java.util.List;
//engy
public class SudokuSolver {

    public int[] solve(int[][] board) throws InvalidGame {
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0)
                    emptyCells.add(new int[]{i, j});
            }
        }
        if (emptyCells.size() != 5) {
            throw new InvalidGame("Solver works only with exactly 5 empty cells.");
        }
        PermutationIterator iterator = new PermutationIterator(5);
        while (iterator.hasNext()) {
            int[] permutation = iterator.next();
             if (BoardVerifier.verifyWithCombination(board, emptyCells, permutation)) {
                return buildSolution(emptyCells, permutation);
            }
        }
        throw new InvalidGame("No valid solution found.");
    }
    private int[] buildSolution(List<int[]> cells, int[] values) {
        int[] result = new int[15];
        int index = 0;
        for (int i = 0; i < 5; i++) {
            result[index++] = cells.get(i)[0];
            result[index++] = cells.get(i)[1];
            result[index++] = values[i];
        }
        return result;
    }
}