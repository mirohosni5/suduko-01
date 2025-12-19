package SudokuSolutionVerifier;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ActionLogger {

    private static final String LOG_FILE = "log.txt";

    // Log one user action
    public static void log(int row, int col, int oldValue, int newValue) {
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.println(
                    "Row=" + row +
                            ", Col=" + col +
                            ", Old=" + oldValue +
                            ", New=" + newValue
            );
        } catch (IOException e) {
            System.out.println("Failed to write to log file");
        }
    }
}
