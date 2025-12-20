package SudokuSolutionVerifier;

import java.util.List;

public class VerificationResult {
    private GameState state;
    private List<Cell> invalidCells;

    public VerificationResult(GameState state, List<Cell> invalidCells) {
        this.state = state;
        this.invalidCells = invalidCells;
    }


}

