package SudokuSolutionVerifier;
/*i used stack here just some push and pop (lifo) last in first out
so push when user edits and pop when user deletes*/
import java.util.*;

public class UndoManager {
    //this is called everytime the user changes anything
    private Stack<UserAction> actions = new Stack<>();
and we will save the old value incase we want to restoreit
    //
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
