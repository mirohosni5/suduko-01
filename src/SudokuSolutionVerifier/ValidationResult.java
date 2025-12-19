/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuSolutionVerifier;

import java.util.*;

public class ValidationResult {

    private List<DuplicateValue> rowDups;
    private List<DuplicateValue> colDups;
    private List<DuplicateValue> boxDups;

    public ValidationResult(List<DuplicateValue> r, List<DuplicateValue> c, List<DuplicateValue> b) {
        this.rowDups = r == null ? new ArrayList<>() : new ArrayList<>(r);
        this.colDups = c == null ? new ArrayList<>() : new ArrayList<>(c);
        this.boxDups = b == null ? new ArrayList<>() : new ArrayList<>(b);
    }

    public List<DuplicateValue> getRowDups() { return Collections.unmodifiableList(rowDups); }
    public List<DuplicateValue> getColDups() { return Collections.unmodifiableList(colDups); }
    public List<DuplicateValue> getBoxDups() { return Collections.unmodifiableList(boxDups); }

    public List<String> getRows() {
        List<String> out = new ArrayList<>();
        for (DuplicateValue dv : rowDups) out.add(dv.toString());
        return out;
    }

    public List<String> getCols() {
        List<String> out = new ArrayList<>();
        for (DuplicateValue dv : colDups) out.add(dv.toString());
        return out;
    }

    public List<String> getBoxes() {
        List<String> out = new ArrayList<>();
        for (DuplicateValue dv : boxDups) out.add(dv.toString());
        return out;
    }

    public boolean isValid() {
        return rowDups.isEmpty() && colDups.isEmpty() && boxDups.isEmpty();
    }
}
