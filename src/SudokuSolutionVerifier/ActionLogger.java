package SudokuSolutionVerifier;

//here the file will be created automatically
import java.io.*;

public class ActionLogger {

    public static void log(int row, int col, int oldValue, int newValue) {
        try { //file with true means append so we wouldnt overwrite files
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
