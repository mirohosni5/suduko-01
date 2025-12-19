package SudokuSolutionVerifier;

import java.util.*;

public class BasicChecks {

    protected int[][] grid;

    public BasicChecks(int[][] g) {
        this.grid = g;
    }

    public List<DuplicateValue> checkRowsDup() {
        List<DuplicateValue> out = new ArrayList<>();
        if (grid == null) return out;
        for (int r = 0; r < 9; r++) {
            Map<Integer, List<Integer>> map = new LinkedHashMap<>();
            for (int c = 0; c < 9; c++) {
                int v = grid[r][c];
                if (v <= 0 || v > 9) continue;
                map.computeIfAbsent(v, k -> new ArrayList<>()).add(c + 1);
            }
            for (Map.Entry<Integer, List<Integer>> e : map.entrySet()) {
                if (e.getValue().size() > 1) out.add(new DuplicateValue("ROW", r + 1, e.getKey(), e.getValue()));
            }
        }
        return out;
    }

    public List<DuplicateValue> checkColsDup() {
        List<DuplicateValue> out = new ArrayList<>();
        if (grid == null) return out;
        for (int c = 0; c < 9; c++) {
            Map<Integer, List<Integer>> map = new LinkedHashMap<>();
            for (int r = 0; r < 9; r++) {
                int v = grid[r][c];
                if (v <= 0 || v > 9) continue;
                map.computeIfAbsent(v, k -> new ArrayList<>()).add(r + 1);
            }
            for (Map.Entry<Integer, List<Integer>> e : map.entrySet()) {
                if (e.getValue().size() > 1) out.add(new DuplicateValue("COL", c + 1, e.getKey(), e.getValue()));
            }
        }
        return out;
    }

    public List<DuplicateValue> checkBoxesDup() {
        List<DuplicateValue> out = new ArrayList<>();
        if (grid == null) return out;
        for (int br = 0; br < 3; br++) {
            for (int bc = 0; bc < 3; bc++) {
                Map<Integer, List<Integer>> map = new LinkedHashMap<>();
                int pos = 0;
                for (int r = br * 3; r < br * 3 + 3; r++) {
                    for (int c = bc * 3; c < bc * 3 + 3; c++) {
                        pos++;
                        int v = grid[r][c];
                        if (v <= 0 || v > 9) continue;
                        map.computeIfAbsent(v, k -> new ArrayList<>()).add(pos);
                    }
                }
                int bindex = br * 3 + bc + 1;
                for (Map.Entry<Integer, List<Integer>> e : map.entrySet()) {
                    if (e.getValue().size() > 1) out.add(new DuplicateValue("BOX", bindex, e.getKey(), e.getValue()));
                }
            }
        }
        return out;
    }

    // convenience methods (for any older code expecting strings)
    public List<String> checkRows() {
        List<String> list = new ArrayList<>();
        for (DuplicateValue dv : checkRowsDup()) list.add(dv.toString());
        return list;
    }

    public List<String> checkCols() {
        List<String> list = new ArrayList<>();
        for (DuplicateValue dv : checkColsDup()) list.add(dv.toString());
        return list;
    }

    public List<String> checkBoxes() {
        List<String> list = new ArrayList<>();
        for (DuplicateValue dv : checkBoxesDup()) list.add(dv.toString());
        return list;
    }
}
