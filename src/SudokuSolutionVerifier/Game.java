package SudokuSolutionVerifier;

public class Game {
    private int[][] board;
    private GameState state;

    public Game(int[][] board) {
        this.board = board;
        this.state = GameState.INCOMPLETE; // default
    }

    public int[][] getBoard() {
        return board;
    }

    public void setCell(int row, int col, int value) {
        board[row][col] = value;
    }

    public int getCell(int row, int col) {
        return board[row][col];
    }

    public GameState getState() {
        return state;
    }

}
