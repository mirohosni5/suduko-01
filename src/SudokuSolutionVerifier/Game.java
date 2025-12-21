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
}
