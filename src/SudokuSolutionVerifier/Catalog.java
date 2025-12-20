package SudokuSolutionVerifier;

import java.util.*;

public class Catalog {
    private List<Game> games;

    public Catalog() {
        games = new ArrayList<>();
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void removeGame(Game game) {
        games.remove(game);
    }
}
