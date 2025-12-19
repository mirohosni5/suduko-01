package SudokuSolutionVerifier;

public class UserAction {
    public int row;
    public int col;
    public int oldValue;

    public UserAction(int r, int c, int old) {
        row = r;
        col = c;
        oldValue = old;
    }
}
