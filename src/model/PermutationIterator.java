package model;
import java.util.Iterator;
import java.util.NoSuchElementException;
//engyz
public class PermutationIterator implements Iterator<int[]> {

    private final int length;     
    private final int maxValue;   
    private final int[] current;
    private boolean hasNext = true;

    public PermutationIterator(int length) {
        this.length = length;
        this.maxValue = 9;
        this.current = new int[length];

       
        for (int i = 0; i < length; i++) {
            current[i] = 1;
        }
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public int[] next() {
        if (!hasNext) {
            throw new NoSuchElementException();
        }

        int[] result = current.clone();
        increment();
        return result;
    }

    private void increment() {
        for (int i = length - 1; i >= 0; i--) {
            if (current[i] < maxValue) {
                current[i]++;
                return;
            } else {
                current[i] = 1;
            }
        }
        hasNext = false;
    }
}
