/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuSolutionVerifier;

public class SudokuVerifier {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Run like: java -jar SudokuVerifier.jar <csv> <mode>");
            return;
        }

        String a = args[0];
        String b = args[1];

        String csvPath;
        int mode = -1;


        try {
            int maybe = Integer.parseInt(a);
            if (maybe == 0 || maybe == 3 || maybe == 27) {
                mode = maybe;
                csvPath = b;
            } else {
                
                try {
                    int maybe2 = Integer.parseInt(b);
                    if (maybe2 == 0 || maybe2 == 3 || maybe2 == 27) {
                        mode = maybe2;
                        csvPath = a;
                    } else {
                        System.out.println("Bad mode. Use 0, 3 or 27.");
                        return;
                    }
                } catch (Exception ex) {
                    System.out.println("Bad mode. Use 0, 3 or 27.");
                    return;
                }
            }
        } catch (Exception ex) {
            
            csvPath = a;
            try {
                int maybe2 = Integer.parseInt(b);
                if (maybe2 == 0 || maybe2 == 3 || maybe2 == 27) {
                    mode = maybe2;
                } else {
                    System.out.println("Bad mode. Use 0, 3 or 27.");
                    return;
                }
            } catch (Exception ex2) {
                System.out.println("Bad mode. Use 0, 3 or 27.");
                return;
            }
        }

        int[][] board;
        try {
            board = CSVReader.load(csvPath);
        } catch (Exception e) {
            System.out.println("Error reading file");
            return;
        }

        SudokuMode m;
        try {
            m = ModesFactory.create(mode);
        } catch (Exception e) {
            System.out.println("Invalid mode");
            return;
        }

        ValidationResult res = m.verify(board);
        ResultPrinter.get().print(res);
    }
}
