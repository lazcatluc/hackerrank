

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
    
    private final Scanner in;
    private final PrintStream out;
    private final String[] args;
    
    public Solution(InputStream in, OutputStream out, String[] args) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out);
        this.args = args;
    }

    public static void main(String[] args) {
        new Solution(System.in, System.out, args).solve();
    }

    public void solve() {
        int arrayLength = in.nextInt();
        int[] arr = new int[arrayLength];
        IntStream.range(0, arrayLength).forEach(i -> arr[i] = in.nextInt());
        IntStream.range(1, arrayLength + 1).forEach(i -> out.print((i == 1 ? "" : " ") + arr[arrayLength - i]));
    }
}
