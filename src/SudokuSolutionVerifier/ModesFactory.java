/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuSolutionVerifier;

public class ModesFactory {

    public static SudokuMode create(int m) {
        if (m == 0) return new SequentialMode();
        if (m == 3) return new ThreeThreadMode();
        if (m == 27) return new TwentySevenThreadMode();
        throw new IllegalArgumentException("Invalid mode");
    }
}
