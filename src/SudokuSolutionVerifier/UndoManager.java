package SudokuSolutionVerifier;
/*i used stack here just some push and pop (lifo) last in first out
so push when user edits and pop when user deletes*/
import View.UserAction;
import java.util.*;

public class UndoManager {
//i created a stack that stores user actions
    private Stack<UserAction> actions = new Stack<>();
/*and we will save the old value incase we want to restoreit
this is called everytime the user changes anything*/
    public void addAction(int row, int col, int oldValue) {
        actions.push(new UserAction(row, col, oldValue, undo().newValue));
    }
//here i will pop the last saved action from the stack
    public UserAction undo() {
       //checking if empty
        if (actions.empty()) {
            return null;
        }
        //then popp
        return actions.pop();
    }
}
