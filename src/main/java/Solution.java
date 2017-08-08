

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
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
        int n = in.nextInt();
        Map<String, Integer> strings = new HashMap<>();
        IntStream.range(0, n).mapToObj(i -> in.next()).forEach(str -> strings.compute(str, (s, prev) -> prev == null ? 1 : prev + 1));
        
        int q = in.nextInt();
        IntStream.range(0, q).forEach(i -> out.println(strings.getOrDefault(in.next(), 0)));
    }
}
