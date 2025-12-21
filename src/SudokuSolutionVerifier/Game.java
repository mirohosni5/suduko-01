package SudokuSolutionVerifier;

public class Game {
    private int[][] board;

    public Game(int[][] board) {
        this.board = board;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getCell(int row, int col) {
        return board[row][col];
    }

    public void setCell(int row, int col, int value) {
        board[row][col] = value;
    }

    public boolean isEmptyCell(int row, int col) {
        return board[row][col] == 0;
    }

    public int countEmptyCells() {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0)
                    count++;
            }
        }
        return count;
    }

    public boolean canSolve() {
        return countEmptyCells() == 5;
    }

}
