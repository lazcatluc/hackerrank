

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Solution {
    
    private final Scanner in;
    private final PrintStream out;
    private final String[] args;
    private final Sequence[] sequences;
    private final int n;
    private final int q;
    private int lastAnswer;
    private boolean lastAnswerChanged;
    
    public Solution(InputStream in, OutputStream out, String[] args) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out);
        this.args = args;
        this.n = this.in.nextInt();
        this.q = this.in.nextInt();
        sequences = new Sequence[n];
        for (int i = 0; i < n; i++) {
            sequences[i] = new Sequence();
        }
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
    
    class AssignQuery implements Query {

        @Override
        public void accept(int x, int y) {
            if (lastAnswerChanged) {
                out.println(); 
            }
            lastAnswer = getWithLastAnswer(x).get(y);
            lastAnswerChanged = true;
            out.print(lastAnswer);
        }
        
    }

    public static void main(String[] args) {
        new Solution(System.in, System.out, args).solve();
    }

    public void solve() {
        Map<Integer, Supplier<Query>> queries = new HashMap<>();
        queries.put(1, AppendQuery::new);
        queries.put(2, AssignQuery::new);
        IntStream.range(0, q).forEach(i -> {
            int query = in.nextInt();
            int x = in.nextInt();
            int y = in.nextInt();
            queries.get(query).get().accept(x, y);
        });
    }
    
    public Sequence getWithLastAnswer(int x) {
        return sequences[(x ^ lastAnswer) % n];
    }
    
    public Sequence getSequence(int i) {
        return sequences[i];
    }
}
