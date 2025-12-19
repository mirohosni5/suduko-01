/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuSolutionVerifier;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class ThreeThreadMode implements SudokuMode {

    public ValidationResult verify(int[][] board) {
        AtomicReference<List<DuplicateValue>> rr = new AtomicReference<>();
        AtomicReference<List<DuplicateValue>> cr = new AtomicReference<>();
        AtomicReference<List<DuplicateValue>> br = new AtomicReference<>();

        Thread t1 = new Thread(() -> rr.set(new BasicChecks(board).checkRowsDup()));
        Thread t2 = new Thread(() -> cr.set(new BasicChecks(board).checkColsDup()));
        Thread t3 = new Thread(() -> br.set(new BasicChecks(board).checkBoxesDup()));

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<DuplicateValue> rows = rr.get(); if (rows == null) rows = new ArrayList<>();
        List<DuplicateValue> cols = cr.get(); if (cols == null) cols = new ArrayList<>();
        List<DuplicateValue> boxes = br.get(); if (boxes == null) boxes = new ArrayList<>();
        return new ValidationResult(rows, cols, boxes);
    }
}
