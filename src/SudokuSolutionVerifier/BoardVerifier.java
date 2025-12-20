package SudokuSolutionVerifier;
//engyz
public class BoardVerifier {

    private final int[][] board;

    public BoardVerifier(int[][] board) {
        this.board = board;
    }

    public boolean isValid() {
        return checkRows() && checkColumns() && checkBoxes();
    }

    private boolean checkRows() {
        for (int i = 0; i < 9; i++) {
            boolean[] seen = new boolean[10];
            for (int j = 0; j < 9; j++) {
                int val = board[i][j];
                if (val == 0) continue;
                if (seen[val]) return false;
                seen[val] = true;
            }
        }
        return true;
    }

    private boolean checkColumns() {
        for (int j = 0; j < 9; j++) {
            boolean[] seen = new boolean[10];
            for (int i = 0; i < 9; i++) {
                int val = board[i][j];
                if (val == 0) continue;
                if (seen[val]) return false;
                seen[val] = true;
            }
        }
        return true;
    }

    private boolean checkBoxes() {
        for (int boxRow = 0; boxRow < 9; boxRow += 3) {
            for (int boxCol = 0; boxCol < 9; boxCol += 3) {
                boolean[] seen = new boolean[10];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int val = board[boxRow + i][boxCol + j];
                        if (val == 0) continue;
                        if (seen[val]) return false;
                        seen[val] = true;
                    }
                }
            }
        }
        return true;
    }
}
