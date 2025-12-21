/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package SudokuSolutionVerifier;

/**
 *
 * @author M
 */
public interface Viewable {
    Catalog getCatalog();
    Game getGame(DifficultyEnum level) throws Exception;
    void driveGames(Game sourceGame) throws Exception;
    String verifyGame(Game game);
    int[] solveGame(Game game) throws Exception;
    void logUserAction(String action) throws Exception;   
}
