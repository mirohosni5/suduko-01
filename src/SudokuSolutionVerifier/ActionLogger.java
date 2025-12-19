package SudokuSolutionVerifier;

import java.io.FileWriter;
import java.io.;

public class ActionLogger {

    public static void log(int row, int col, int oldValue, int newValue) {
        try {
            FileWriter fw = new FileWriter("log.txt", true);
            fw.write("row=" + row +
                    ", col=" + col +
                    ", old=" + oldValue +
                    ", new=" + newValue + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing log file");
        }
    }
}
