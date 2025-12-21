package SudokuSolutionVerifier;

import java.util.*;

public class SequentialMode {

    public ValidationResult verify(int[][] board) {
        BasicChecks ck = new BasicChecks(board);
        List<DuplicateValue> rd = ck.checkRowsDup();
        List<DuplicateValue> cd = ck.checkColsDup();
        List<DuplicateValue> bd = ck.checkBoxesDup();
        return new ValidationResult(rd, cd, bd);
    }
}
