/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package SudokuSolutionVerifier;

/**
 *
 * @author M
 */
public interface controllable {
    boolean getCatalog();
    boolean[][] verifyGame(int[][] game);
    int[][] getGame(char level) throws Exception;
    int[][] solveGame(int[][] game) throws Exception;
    void driveGames(String sourcePath) throws Exception;
    void logUserAction(UserAction action) throws Exception; 
}
