package SudokuSolutionVerifier;

import java.util.*;

public class TwentySevenThreadMode implements SudokuMode {

    public ValidationResult verify(int[][] board) {
        List<DuplicateValue> rowDups = Collections.synchronizedList(new ArrayList<>());
        List<DuplicateValue> colDups = Collections.synchronizedList(new ArrayList<>());
        List<DuplicateValue> boxDups = Collections.synchronizedList(new ArrayList<>());

        List<DuplicateValue> allRows = new BasicChecks(board).checkRowsDup();
        List<DuplicateValue> allCols = new BasicChecks(board).checkColsDup();
        List<DuplicateValue> allBoxes = new BasicChecks(board).checkBoxesDup();

        Thread[] rthreads = new Thread[9];
        Thread[] cthreads = new Thread[9];
        Thread[] bthreads = new Thread[9];

        for (int i = 0; i < 9; i++) {
            final int idx = i + 1;
            rthreads[i] = new Thread(() -> {
                for (DuplicateValue dv : allRows) if (dv.getIndex() == idx) rowDups.add(dv);
            });
            cthreads[i] = new Thread(() -> {
                for (DuplicateValue dv : allCols) if (dv.getIndex() == idx) colDups.add(dv);
            });
            bthreads[i] = new Thread(() -> {
                for (DuplicateValue dv : allBoxes) if (dv.getIndex() == idx) boxDups.add(dv);
            });
        }

        for (int i = 0; i < 9; i++) {
            rthreads[i].start();
            cthreads[i].start();
            bthreads[i].start();
        }

        try {
            for (int i = 0; i < 9; i++) {
                rthreads[i].join();
                cthreads[i].join();
                bthreads[i].join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return new ValidationResult(rowDups, colDups, boxDups);
    }
}
