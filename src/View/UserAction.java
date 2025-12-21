package View;

public class UserAction {
    public int row;
    public int col;
    public int oldValue;

    public UserAction(int r, int c, int old) {
        row = r;
        col = c;
        oldValue = old;
    }
    @Override
    public String toString() {
        return row + "," + col + "," + oldValue;
    }
    public static UserAction fromString(String str) {
        String[] parts = str.split(",");
        return new UserAction(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),Integer.parseInt(parts[2]));
    }
}
