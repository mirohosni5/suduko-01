package SudokuSolutionVerifier;

import java.util.*;

public class UndoManager {

    private Stack<UserAction> actions = new Stack<>();

    public void addAction(int row, int col, int oldValue) {
        actions.push(new UserAction(row, col, oldValue));
    }

    public UserAction undo() {
        if (actions.empty()) {
            return null;
        }
        return actions.pop();
    }
}
