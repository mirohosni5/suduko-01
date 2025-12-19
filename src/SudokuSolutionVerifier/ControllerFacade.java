package SudokuSolutionVerifier;

public class ControllerFacade {

    private int[][] board;
    private UndoManager undoManager;

    public ControllerFacade(int[][] board) {
        this.board = board;
        undoManager = new UndoManager();
    }


    public void changeCell(int row, int col, int value) {

        int oldValue = board[row][col];

        // we are saving here for the undo ascton
        undoManager.addAction(row, col, oldValue);

        // we are writing here for the log file..
        ActionLogger.log(row, col, oldValue, value);

        // updating the board
        board[row][col] = value;
    }

    // 
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
