package SudokuSolutionVerifier;

public class ResultPrinter {

    private static ResultPrinter inst;

    private ResultPrinter() {}

    public static ResultPrinter get() {
        if (inst == null) inst = new ResultPrinter();
        return inst;
    }

    public void print(ValidationResult r) {
        if (r == null) {
            System.out.println("VALIDATION ERROR");
            return;
        }
        if (r.isValid()) {
            System.out.println("VALID");
            return;
        }
        System.out.println("INVALID");
        for (String s : r.getRows()) System.out.println(s);
        System.out.println("------------------------------------------");
        for (String s : r.getCols()) System.out.println(s);
        System.out.println("------------------------------------------");
        for (String s : r.getBoxes()) System.out.println(s);
    }
}
