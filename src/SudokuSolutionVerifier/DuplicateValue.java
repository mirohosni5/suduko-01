/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuSolutionVerifier;

import java.util.*;

public class DuplicateValue {
    private String kind; // "ROW", "COL", "BOX"
    private int index;   // 1-based row/col/box index
    private int value;
    private List<Integer> positions;

    public DuplicateValue(String kind, int index, int value, List<Integer> positions) {
        this.kind = kind;
        this.index = index;
        this.value = value;
        this.positions = positions == null ? new ArrayList<>() : new ArrayList<>(positions);
    }

    public String getKind() { return kind; }
    public int getIndex() { return index; }
    public int getValue() { return value; }
    public List<Integer> getPositions() { return Collections.unmodifiableList(positions); }

    public String toString() {
        return kind + " " + index + ", #" + value + ", " + positions.toString();
    }
}
