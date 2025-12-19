package SudokuSolutionVerifier;

public class ControllerFacade {

    private int[][] board;
    private UndoManager undoManager;

    public ControllerFacade(int[][] board) {
        this.board = board;
        undoManager = new UndoManager();
    }

    // called when user edits a cell
    public void changeCell(int row, int col, int value) {

        int oldValue = board[row][col];

        // save for undo
        undoManager.addAction(row, col, oldValue);

        // write to log file
        ActionLogger.log(row, col, oldValue, value);

        // update board
        board[row][col] = value;
    }

    // undo last move
    public void undo() {
        UserAction action = undoManager.undo();
        if (action != null) {
            board[action.row][action.col] = action.oldValue;
        }
    }

    public int[][] getBoard() {
        return board;
    }
}
