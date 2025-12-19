package SudokuSolutionVerifier;
//i used stack here just some push and pop
import java.util.*;

public class UndoManager {

    private Stack<UserAction> actions = new Stack<>();
//this is called 
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
