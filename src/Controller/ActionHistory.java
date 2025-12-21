package Controller;

import java.util.Stack;

public class ActionHistory {
    private Stack<UserAction> stack = new Stack<>();

    public void push(UserAction a) {
        stack.push(a);
    }

}
