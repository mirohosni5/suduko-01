package View;

public class UserAction {
    public int row, col, oldValue, newValue;

    public UserAction(int r, int c, int oldV, int newV) {
        row = r;
        col = c;
        oldValue = oldV;
        newValue = newV;
    }

    @Override
    public String toString() {
        return row + "," + col + "," + oldValue + "," + newValue;
    }

    public static UserAction fromString(String s) {
        String[] parts = s.split(",");
        int r = Integer.parseInt(parts[0]);
        int c = Integer.parseInt(parts[1]);
        int oldV = Integer.parseInt(parts[2]);
        int newV = Integer.parseInt(parts[3]);
        return new UserAction(r, c, oldV, newV);
    }
}

