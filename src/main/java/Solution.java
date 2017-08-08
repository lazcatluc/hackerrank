

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution {
    
    private final Scanner in;
    private final PrintStream out;
    private final String[] args;
    private final Sequence[] sequences;
    private final int n;
    private final int q;
    private int lastAnswer;
    
    public Solution(InputStream in, OutputStream out, String[] args) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out);
        this.args = args;
        this.n = this.in.nextInt();
        this.q = this.in.nextInt();
        sequences = new Sequence[n];
        Arrays.fill(sequences, new Sequence());
    }
    
    class Sequence {
        private List<Integer> list = new ArrayList<>();
        
        boolean append(int y) {
            return list.add(y);
        }
        
        int get(int y) {
            return list.get(y % list.size());
        }
    }
    
    @FunctionalInterface
    interface Query {
        void accept(int x, int y);
    }
    
    class AppendQuery implements Query {

        @Override
        public void accept(int x, int y) {
            getWithLastAnswer(x).append(y);
        }
        
    }

    public static void main(String[] args) {
        new Solution(System.in, System.out, args).solve();
    }

    public void solve() {
        // TODO Auto-generated method stub
        
    }
    
    public Sequence getWithLastAnswer(int x) {
        return sequences[(x ^ lastAnswer) % n];
    }
    
    public Sequence getSequence(int i) {
        return sequences[i];
    }
}
