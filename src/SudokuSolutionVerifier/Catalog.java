package SudokuSolutionVerifier;

import java.util.*;

public class Catalog {
    private boolean current;
    private boolean allModesExist;

    public Catalog(boolean current, boolean allModesExist) {
        this.current = current;
        this.allModesExist = allModesExist;
    }

    public boolean hasCurrentGame() {
        return current;
    }

}
