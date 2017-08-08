

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
    
    private final Scanner in;
    private final PrintStream out;
    private final String[] args;
    private final int n;
    private final int d;
    
    public Solution(InputStream in, OutputStream out, String[] args) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out);
        this.args = args;
        this.n = this.in.nextInt();
        this.d = this.in.nextInt();
    }

    public static void main(String[] args) {
        new Solution(System.in, System.out, args).solve();
    }

    static class Rotator {
        private final int[] rotated;
        private int currentPosition = 0;
        
        public Rotator(int... original) {
            rotated = new int[original.length * 2];
            System.arraycopy(original, 0, rotated, 0, original.length);
            System.arraycopy(original, 0, rotated, original.length, original.length);
        }

        public void rotateLeft() {
            rotateLeft(1);
        }
        
        public void rotateLeft(int d) {
            currentPosition += d;
        }

        public List<Integer> getRotated() {
            List<Integer> get = new ArrayList<>(rotated.length / 2);
            IntStream.range(currentPosition, currentPosition + rotated.length / 2).map(i -> rotated[i]).forEach(get::add);
            return get;
        }
    }
    
    public void solve() {
        int[] arr = new int[n];
        IntStream.range(0, n).forEach(i -> arr[i] = in.nextInt());
        Rotator rotator = new Rotator(arr);
        rotator.rotateLeft(d);
        out.print(rotator.getRotated().toString().replaceAll("[\\[\\],]",""));
    }
}
