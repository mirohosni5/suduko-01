package SudokuSolutionVerifier;

public class Game {
    private int[][] board;
    private GameState state;

    public Game(int[][] board) {
        this.board = board;
        this.state = GameState.INCOMPLETE; // default
    }
}
